package fr.deroffal.bibliotheque.securite.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

import java.time.Duration
import java.util.function.Function

import static io.jsonwebtoken.SignatureAlgorithm.HS512

@Service
class JwtTokenService {

    private static final long JWT_TOKEN_VALIDITY = Duration.ofHours(1).toMillis()

    private final JwtConfig jwtConfig
    private final JwtParser parser

    JwtTokenService(final JwtConfig jwtConfig, final JwtParser parser) {
        this.jwtConfig = jwtConfig
        this.parser = parser
    }

    String getUsernameFromToken(final String token) {
        getClaimFromToken(token) { it.getSubject() }
    }

    Date getExpirationDateFromToken(final String token) {
        getClaimFromToken(token) { it.getExpiration() }
    }

    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = parser.parseClaimsJws(token).body
        claimsResolver.apply(claims)
    }

    String generateToken(final UserDetails userDetails, final Map<String, Object> additionalClaims = [:]) {
        doGenerateToken(userDetails.username, additionalClaims)
    }

    /**
     * //while creating the token -
     * //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * //2. Sign the JWT using the HS512 algorithm and secret key.
     * //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compaction of the JWT to a URL-safe string
     **/
    private String doGenerateToken(final String subject, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(HS512, jwtConfig.secret)
                .compact()
    }

    boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token)
        expiration < new Date()
    }
}
