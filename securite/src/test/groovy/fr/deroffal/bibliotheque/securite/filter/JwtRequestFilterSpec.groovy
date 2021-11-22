package fr.deroffal.bibliotheque.securite.filter

import groovy.time.TimeCategory
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static io.jsonwebtoken.SignatureAlgorithm.HS512

@SpringBootTest(
        classes = [JwtRequestFilter, JwtConfig, JwtTokenService],
        properties = ["jwt.secret=lJXtk18CBKOyMxuLDZj1ACQqM4jdLTUAIUNng3PmLLbRSPbAraqdGzzDEazFYIZql1MsgHYHVIkUpkjhJcs0EOcGiBdJtsmJXssO"]
)
class JwtRequestFilterSpec extends Specification {

    private final static String TOKEN = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3ZWJhcHAiLCJleHAiOjE2MzU4NTg3OTksImlhdCI6MTYzNTg1NTE5OX0.mfyF85vDunv93NToQl19V_FmLqZrZzKMJjXhNvjAdMucAtP-DMWyDTQZqS-1uRLYmE7PP0sUqnR-Q0HwJHNNzw'

    @Autowired
    JwtRequestFilter jwtRequestFilter

    @Autowired
    JwtParser jwtParser

    @SpringBean
    UserDetailsService userDetailsService = Stub()

    def setup() {
        SecurityContextHolder.context.authentication = null
    }

    def "doFilterInternal avec un utilisateur"() {
        given:
        String username = "user"
        HttpServletRequest request = Stub(HttpServletRequest) {
            getHeader("Authorization") >> newTokenFor(username)
        }
        HttpServletResponse response = Stub(HttpServletResponse)
        FilterChain chain = Mock(FilterChain)

        and:
        UserDetails userDetails = newUserDetails(username)
        userDetailsService.loadUserByUsername(username) >> userDetails

        when:
        jwtRequestFilter.doFilterInternal(request, response, chain)

        then:
        def authentication = SecurityContextHolder.context.authentication
        authentication.principal == userDetails

        and:
        1 * chain.doFilter(request, response)
    }

    def "doFilterInternal avec un token errone"() {
        given:
        String username = "user"
        HttpServletRequest request = Stub(HttpServletRequest) {
            getHeader("Authorization") >> token
        }
        HttpServletResponse response = Stub(HttpServletResponse)
        FilterChain chain = Mock(FilterChain)

        and:
        UserDetails userDetails = newUserDetails(username)
        userDetailsService.loadUserByUsername(username) >> userDetails

        when:
        jwtRequestFilter.doFilterInternal(request, response, chain)

        then:
        def authentication = SecurityContextHolder.context.authentication
        authentication == authenticationAttendue

        and:
        1 * chain.doFilter(request, response)

        where:
        token                           || authenticationAttendue
        TOKEN                           || null
        null                            || null
        ''                              || null
        'pas un token'                  || null
        newTokenNDaysBefore("user", 10) || null
    }

    @Unroll
    def "doFilterInternal avec un token bon mais #cas"() {
        given:
        String username = "user"
        HttpServletRequest request = Stub(HttpServletRequest) {
            getHeader("Authorization") >> token
        }
        HttpServletResponse response = Stub(HttpServletResponse)
        FilterChain chain = Mock(FilterChain)

        and:
        UserDetails userDetails = newUserDetails(username)
        userDetailsService.loadUserByUsername(username) >> userDetails

        when:
        jwtRequestFilter.doFilterInternal(request, response, chain)

        then:
        def authentication = SecurityContextHolder.context.authentication
        authentication == authenticationAttendue

        and:
        1 * chain.doFilter(request, response)

        where:
        cas                   | token                           || authenticationAttendue
        'non-valide (date)'   | newTokenNDaysBefore("user", 10) || null
        'mauvais utilisateur' | newTokenFor("autre user")       || null
    }

    private String newTokenNDaysBefore(final String subject, final int n) {
        final Date issuedAt = use(TimeCategory) {
            new Date() - n.days
        }
        return newTokenFor(subject, issuedAt)

    }

    private String newTokenFor(final String subject, Date issuedAt = new Date()) {
        final Date expiration = use(TimeCategory) {
            issuedAt + 1.days
        }

        def token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(HS512, 'lJXtk18CBKOyMxuLDZj1ACQqM4jdLTUAIUNng3PmLLbRSPbAraqdGzzDEazFYIZql1MsgHYHVIkUpkjhJcs0EOcGiBdJtsmJXssO')
                .compact()
        return "Bearer $token"
    }

    private UserDetails newUserDetails(final String username) {
        Stub(UserDetails) {
            getUsername() >> username
        }
    }
}
