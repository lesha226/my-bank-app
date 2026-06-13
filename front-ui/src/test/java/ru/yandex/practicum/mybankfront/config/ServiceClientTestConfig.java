package ru.yandex.practicum.mybankfront.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class ServiceClientTestConfig {

    @Bean
    //@Primary
    public WebClient.Builder serviceClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token");
    }
}