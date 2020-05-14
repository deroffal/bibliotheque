package fr.deroffal.bibliotheque.api.authentification.securite;

import fr.deroffal.bibliotheque.api.authentification.securite.details.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private static final String BEARER = "Bearer ";

    private WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException {
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
        } catch (IllegalArgumentException e) {
            logger.debug("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            logger.debug("JWT Token has expired");
        }
        return null;
    }

    private void authenticate(HttpServletRequest request, String jwtToken, String username) {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(webAuthenticationDetailsSource.buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    private static boolean isAuthorizationHeaderValid(final String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(BEARER);
    }
}
