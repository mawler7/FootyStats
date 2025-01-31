package com.footystars.security;

import com.footystars.model.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.footystars.utils.LogsNames.APPLICATION_JSON;
import static com.footystars.utils.LogsNames.AUTHORIZATION_HEADER;
import static com.footystars.utils.LogsNames.BEARER;
import static com.footystars.utils.LogsNames.TOKEN_EXPIRED;
import static com.footystars.utils.LogsNames.TOKEN_LENGTH;

/**
 * JWT authentication filter that intercepts every request to verify and set authentication.
 * This filter ensures that only authenticated requests are processed further.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    /**
     * Filters incoming requests to extract and validate the JWT token.
     * If the token is valid, it sets authentication in the security context.
     *
     * @param request     the HTTP request containing the JWT token in the `Authorization` header.
     * @param response    the HTTP response used to send an error response if the token is expired.
     * @param filterChain the filter chain to proceed with the next filter in the chain.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        // If the Authorization header is missing or does not contain a Bearer token, proceed without authentication
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token from the header
        String token = authHeader.substring(TOKEN_LENGTH);
        String userEmail;

        try {
            // Extract username (email) from the token
            userEmail = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            // Return an error response if the token is expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(TOKEN_EXPIRED);
            return;
        }

        // If the user is not authenticated, validate and set authentication in the security context
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = (User) userService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the request processing
        filterChain.doFilter(request, response);
    }
}
