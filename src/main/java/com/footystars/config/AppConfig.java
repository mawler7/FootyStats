package com.footystars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

import static com.footystars.utils.LogsNames.*;

/**
 * Configuration class for application-wide beans and async execution settings.
 */
@Configuration
@EnableAsync
public class AppConfig implements AsyncConfigurer {

    /**
     * Configures and provides an instance of {@link OkHttpClient} for making HTTP requests.
     *
     * @return an instance of {@link OkHttpClient}.
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Configures and provides an instance of {@link ObjectMapper} with custom settings.
     * Registers modules for Java Time and Hibernate compatibility.
     *
     * @return a configured {@link ObjectMapper} instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Hibernate6Module());
        return objectMapper;
    }

    /**
     * Customizes the default Jackson configuration for Spring Boot.
     * Registers additional modules and disables timestamp serialization for dates.
     *
     * @return a {@link Jackson2ObjectMapperBuilderCustomizer} to modify Jackson settings.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.modules(new JavaTimeModule());
            builder.modules(new Hibernate6Module());
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

    /**
     * Configures and provides an executor for asynchronous tasks.
     * Defines thread pool settings such as core pool size, max pool size, and queue capacity.
     *
     * @return an {@link Executor} instance for handling async tasks.
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadGroupName(THREAD_GROUP_NAME);
        executor.initialize();
        return executor;
    }

    /**
     * Configures and provides an instance of {@link RestTemplate} for making REST API calls.
     *
     * @return a configured {@link RestTemplate} instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
