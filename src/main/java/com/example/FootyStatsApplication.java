package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.foot8", "com.example.foot8.persistence"})
@EntityScan
@EnableJpaRepositories(basePackages = "com.example.foot8.persistence.repository")
@EnableAsync
public class FootyStatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootyStatsApplication.class, args);
    }
}