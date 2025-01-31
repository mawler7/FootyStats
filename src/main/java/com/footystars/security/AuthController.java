package com.footystars.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * Controller responsible for handling authentication-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Handles a successful Google OAuth2 login.
     * Redirects the user to the frontend with the generated JWT token.
     *
     * @param response {@link HttpServletResponse} used for redirecting the user
     *                 with the authentication token.
     * @throws IOException if an I/O error occurs during redirection.
     */
    @GetMapping("/oauth2/success")
    public void handleGoogleLoginSuccess(HttpServletResponse response) throws IOException {
        String token = authenticationService.handleGoogleLoginSuccess();
        response.sendRedirect("http://localhost:3000?token=" + token);
    }

    /**
     * Verifies the validity of the provided JWT token.
     * The token is extracted from the `Authorization` header of the request.
     *
     * @param request {@link HttpServletRequest} containing the `Authorization` header
     *                with the JWT token.
     * @return {@link ResponseEntity} with HTTP 200 OK if the token is valid,
     *         or HTTP 401 UNAUTHORIZED if the token is invalid.
     */
    @GetMapping("/verify")
    public ResponseEntity<Void> verifyToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        boolean isValid = authenticationService.verifyToken(token);
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
