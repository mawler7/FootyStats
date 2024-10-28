package com.footystars.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.footystars.utils.LogsNames.TOKEN_REDIRECT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationService authenticationService;

    @GetMapping("/oauth2/success")
    public void handleGoogleLoginSuccess(HttpServletResponse response) throws IOException {
        String token = authenticationService.handleGoogleLoginSuccess();
        response.sendRedirect(TOKEN_REDIRECT + token);
    }
}