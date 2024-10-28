package com.footystars;

import com.footystars.config.api.AuthConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories(basePackages = "com.footystars.persistence.repository")
@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({AuthConfig.class })
public class Foot8 {
    public static void main(String[] args) {
        SpringApplication.run(Foot8.class, args);
    }
}