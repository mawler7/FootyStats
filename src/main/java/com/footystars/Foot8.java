package com.footystars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = {"com.footystars.foot8"})
@EnableJpaRepositories(basePackages = "com.footystars.foot8.repository")
@SpringBootApplication
public class Foot8 {
    public static void main(String[] args) {
        SpringApplication.run(Foot8.class, args);
    }
}