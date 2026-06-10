package ru.yandex.practicum.mybank.service.cash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;
import java.util.Map;

//@Configuration
//@Profile("test-contract")
@TestConfiguration
public class AccountsClientTestConfig {

    /*@Bean
    //@Primary
    public WebClient accountsWebClient(@Value("${bank.service.accounts.base-url}") String accountsServiceBaseUrl) {
        return WebClient.builder()
                .baseUrl(accountsServiceBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token")
                .build();
    }*/

    @Bean
    //@Primary
    public WebClient.Builder accountsWebClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token");
    }

}
