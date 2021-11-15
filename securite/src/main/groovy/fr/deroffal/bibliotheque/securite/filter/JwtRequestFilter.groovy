package fr.deroffal.bibliotheque.securite.filter

import groovy.util.logging.Slf4j
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer "
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization"

    private final WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource()
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    JwtRequestFilter(final UserDetailsService userDetailsService, final JwtTokenService jwtTokenService) {
        this.userDetailsService = userDetailsService
        this.jwtTokenService = jwtTokenService
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {
        final String jwtToken = getJwtToken(request)
        final String username = getUsername(jwtToken)

        authenticate(request, jwtToken, username)

        chain.doFilter(request, response)
    }

    private static String getJwtToken(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME)
        return authorizationHeader?.startsWith(BEARER) ? authorizationHeader.substring(BEARER.length()) : null
    }

    private String getUsername(final String jwtToken) {
        try {
            return jwtToken != null ? jwtTokenService.getUsernameFromToken(jwtToken) : null
        } catch (final IllegalArgumentException e) {
            logger.debug("Unable to get JWT Token");
        } catch (final ExpiredJwtException e) {
            logger.debug("JWT Token has expired");
        }
        return null
    }

    private void authenticate(final HttpServletRequest request, final String jwtToken, final String username) {
        if (username != null && SecurityContextHolder.context.authentication == null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenService.isTokenValid(jwtToken, userDetails)) {
                SecurityContextHolder.context.authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                        .tap { details = webAuthenticationDetailsSource.buildDetails(request) }
            }
        }
    }

}
