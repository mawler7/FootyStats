package com.footystars.config.api;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "google")
public class AuthConfig {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${AUTH_PROVIDER}")
    private String authProvider;

    @Value("${AUTH_URI}")
    private String authUri;

    @Value("${PROJECT_ID}")
    private String projectId;

    @Value("${TOKEN_URI}")
    private String tokenUri;

}
