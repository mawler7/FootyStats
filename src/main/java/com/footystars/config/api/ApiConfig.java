package com.footystars.config.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing API-related properties.
 * This class loads API configuration values from the application's environment or properties file.
 */
@Configuration
@ConfigurationProperties(prefix = "rapidapi")
@Getter
@Setter
public class ApiConfig {

    /**
     * The API key used for authenticating requests to the external API.
     */
    @Value("${API_KEY}")
    private String apiKey;

    /**
     * The API host URL or domain for making requests.
     */
    @Value("${API_HOST}")
    private String apiHost;

    /**
     * The default timezone setting for API requests.
     */
    @Value("${API_TIMEZONE}")
    private String timezone;
}
