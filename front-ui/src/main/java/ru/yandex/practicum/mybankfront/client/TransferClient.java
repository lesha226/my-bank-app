package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;

@Component
public class TransferClient {

    private final String base_url;
    private final WebClient webClient;

    public TransferClient(
            @Value("${bank.service.transfer.base-url}") String baseUrl,
            WebClient.Builder webClientBuilder
    ) {
        base_url = baseUrl;
        this.webClient = webClientBuilder.baseUrl(base_url).build();
    }

    public void transfer(String login, TransferRequest request) {
        System.out.println("TransferClient.transfer: login=" + login + ", request=" + request + ", base_url=" + base_url);

        webClient.post()
                .uri("/transfer/{login}", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
