package fr.deroffal.bibliotheque.authentification.securite;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
        throws ServletException, IOException {
        final String jwtToken = getJwtToken(request);
        final String username = getUsername(jwtToken);

        authenticate(request, jwtToken, username);

        chain.doFilter(request, response);
    }

    private static String getJwtToken(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        return isAuthorizationHeaderValid(authorizationHeader) ? authorizationHeader.substring(BEARER.length()) : null;
    }

    private String getUsername(final String jwtToken) {
        try {
            return jwtToken != null ? jwtTokenService.getUsernameFromToken(jwtToken) : null;
        } catch (final IllegalArgumentException e) {
            logger.debug("Unable to get JWT Token");
        } catch (final ExpiredJwtException e) {
            logger.debug("JWT Token has expired");
        }
        return null;
    }

    private void authenticate(final HttpServletRequest request, final String jwtToken, final String username) {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenService.isTokenValid(jwtToken, userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(webAuthenticationDetailsSource.buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    private static boolean isAuthorizationHeaderValid(final String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(BEARER);
    }
}
