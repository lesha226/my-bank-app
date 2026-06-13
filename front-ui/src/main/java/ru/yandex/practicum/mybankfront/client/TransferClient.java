package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybankfront.client.dto.TransferClientRequest;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;

@Component
public class TransferClient {

    private final String base_url;
    private final WebClient webClient;

    public TransferClient(
            @Value("${bank.service.transfer.base-url}") String baseUrl,
            WebClient.Builder serviceClientBuilder
    ) {
        base_url = baseUrl;
        this.webClient = serviceClientBuilder.baseUrl(base_url).build();
    }

    public ServiceResponse transfer(String login, TransferRequest request) {
        System.out.println("TransferClient.transfer: login=" + login + ", request=" + request + ", base_url=" + base_url);

        TransferClientRequest transferClientRequest = new TransferClientRequest(request.recipient(), request.value());

        return webClient.post()
                .uri("/transfer/{login}", login)
                .bodyValue(transferClientRequest)
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .block();
    }
}
