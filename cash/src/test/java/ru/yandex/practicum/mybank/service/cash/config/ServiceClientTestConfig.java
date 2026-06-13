package ru.yandex.practicum.mybank.service.cash.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
//@Profile("test-contract")
@TestConfiguration
public class ServiceClientTestConfig {

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
    public WebClient.Builder serviceClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token");
    }

}
