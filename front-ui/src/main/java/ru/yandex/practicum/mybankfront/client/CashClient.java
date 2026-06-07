package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

@Component
public class CashClient {

    private final String base_url;
    private final WebClient webClient;

    public CashClient(
            @Value("${bank.service.cash.base-url}") String baseUrl,
            WebClient.Builder webClientBuilder
    ) {
        base_url = baseUrl;
        this.webClient = webClientBuilder.baseUrl(base_url).build();
    }

    public void action(String login, EditCashRequest request) {
        System.out.println("CashClient.action: login=" + login + ", request=" + request + ", base_url=" + base_url);

        webClient.post()
                .uri("/cash/{login}/action", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
