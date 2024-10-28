package com.footystars.config.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rapidapi")
@Getter
@Setter
public class RapidApiConfig {

    @Value("${API_KEY}")
    private String apiKey;

    @Value("${API_HOST}")
    private String apiHost;

    @Value("${API_TIMEZONE}")
    private String timezone;

}
