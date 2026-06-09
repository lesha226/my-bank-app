package ru.yandex.practicum.mybank.service.transfer.config;

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
public class WebClientBuilderTestConfig {

    @Bean
    //@Primary
    public WebClient.Builder testWebClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token");
    }

    @Bean
    //@Primary
    public JwtDecoder jwtDecoder() {
        return token -> {
            Instant now = Instant.now();

            return Jwt.withTokenValue(token)
                    .header("alg", "none")
                    .subject("contract-test")
                    .claim("realm_access", Map.of(
                            "roles", List.of("SERVICE")
                    ))
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(3600))
                    .build();
        };
    }

}
