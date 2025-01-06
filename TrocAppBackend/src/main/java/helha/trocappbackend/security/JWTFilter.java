package helha.trocappbackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import helha.trocappbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filter for processing JSON Web Tokens (JWT) in incoming HTTP requests.
 * This filter extends {@link OncePerRequestFilter}, ensuring it is executed once per request.
 * It extracts the JWT from the Authorization header, validates it, and sets the authentication
 * context for the current thread if the token is valid.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    /**
     * Utility class for generating, validating, and parsing JWT tokens.
     */
    @Autowired
    private JWTUtils jwtUtils;

    /**
     * Service for loading user-specific data during authentication.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Filters incoming requests to process JWT tokens.
     * If a valid JWT is found, it sets the authentication context with user details.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param filterChain the filter chain to continue processing the request.
     * @throws ServletException if an error occurs during filtering.
     * @throws IOException if an I/O error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = parseJWTFromHeader(request);
        if (jwt != null && jwtUtils.validateToken(jwt)) {
            Claims claims = jwtUtils.parseToken(jwt);
            String username = claims.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request.
     * @return the JWT as a string if present, or {@code null} if not.
     */
    private String parseJWTFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
