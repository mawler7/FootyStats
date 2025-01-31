package com.footystars.config.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing Google OAuth authentication properties.
 * This class loads authentication-related properties from the application's environment or properties file.
 */
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "google")
public class AuthConfig {

    /**
     * The client ID used for Google OAuth authentication.
     */
    @Value("${CLIENT_ID}")
    private String clientId;

    /**
     * The client secret used for Google OAuth authentication.
     */
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    /**
     * The authentication provider URL or identifier.
     */
    @Value("${AUTH_PROVIDER}")
    private String authProvider;

    /**
     * The authentication URI used for initiating OAuth authentication.
     */
    @Value("${AUTH_URI}")
    private String authUri;

    /**
     * The Google Cloud project ID associated with the authentication configuration.
     */
    @Value("${PROJECT_ID}")
    private String projectId;

    /**
     * The token URI used for exchanging authentication codes for access tokens.
     */
    @Value("${TOKEN_URI}")
    private String tokenUri;
}
