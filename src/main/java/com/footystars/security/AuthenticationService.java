package com.footystars.security;

import com.footystars.model.entity.User;
import com.footystars.persistence.repository.UserRepository;
import com.footystars.utils.LogsNames;
import com.footystars.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.footystars.utils.LogsNames.EMAIL;
import static com.footystars.utils.LogsNames.FAMILY_NAME;
import static com.footystars.utils.LogsNames.GIVEN_NAME;
import static com.footystars.utils.LogsNames.TOKEN_LENGTH;
import static com.footystars.utils.LogsNames.USER_NOT_AUTHENTICATED;

/**
 * Service responsible for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * Handles the successful authentication of a user via Google OAuth2.
     * If the user does not exist in the database, a new user entry is created.
     * Generates a JWT token for the authenticated user.
     *
     * @return the generated JWT token for the authenticated user.
     * @throws IllegalStateException if the user is not authenticated.
     */
    public String handleGoogleLoginSuccess() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauthUser = oauthToken.getPrincipal();
            String email = oauthUser.getAttribute(EMAIL);

            // Check if user exists; if not, create a new entry
            userRepository.findByEmail(email)
                    .ifPresentOrElse(
                            user -> {
                                // User already exists, no action needed
                            },
                            () -> userRepository.save(
                                    User.builder()
                                            .email(email)
                                            .firstName(oauthUser.getAttribute(GIVEN_NAME))
                                            .lastName(oauthUser.getAttribute(FAMILY_NAME))
                                            .role(List.of(Role.USER))
                                            .build()
                            )
                    );
            return jwtService.generateToken(email);
        }
        throw new IllegalStateException(USER_NOT_AUTHENTICATED);
    }

    /**
     * Verifies the validity of a JWT token.
     * If the token starts with "Bearer ", it is trimmed before validation.
     *
     * @param token the JWT token to be validated.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public boolean verifyToken(String token) {
        if (token != null && token.startsWith(LogsNames.BEARER)) {
            token = token.substring(TOKEN_LENGTH);
        }
        try {
            return jwtService.validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }
}
