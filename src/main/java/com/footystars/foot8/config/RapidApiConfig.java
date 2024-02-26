package com.footystars.foot8.config;

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

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;

    @Value("${RAPIDAPI_TIMEZONE}")
    private String timezone;

}
