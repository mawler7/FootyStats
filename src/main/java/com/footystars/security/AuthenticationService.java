package com.footystars.security;

import com.footystars.model.entity.User;
import com.footystars.persistence.repository.UserRepository;
import com.footystars.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public String handleGoogleLoginSuccess() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauthUser = oauthToken.getPrincipal();
            String email = oauthUser.getAttribute("email");


            userRepository.findByEmail(email)
                    .ifPresentOrElse(
                            user -> {
                            },
                            () -> userRepository.save(
                                    User.builder()
                                            .email(email)
                                            .firstName(oauthUser.getAttribute("given_name"))
                                            .lastName(oauthUser.getAttribute("family_name"))
                                            .role(List.of(Role.USER))
                                            .build()
                            )
                    );

            return jwtService.generateToken(email);


        }
        throw new IllegalStateException("User is not authenticated");
    }
}