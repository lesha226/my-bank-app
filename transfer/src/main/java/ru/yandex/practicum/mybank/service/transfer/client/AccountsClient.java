package ru.yandex.practicum.mybank.service.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybank.service.transfer.client.dto.AccountsTransferRequest;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;

@Component
public class AccountsClient {

    private final String base_url;
    private final WebClient webClient;


    public AccountsClient(
            @Value("${bank.service.accounts.base-url}") String baseUrl,
            WebClient.Builder webClientBuilder
    ) {
        base_url = baseUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public TransferResponse transfer(AccountsTransferRequest request) {
        System.out.println("AccountClient.transfer: request=" + request + ", base_url=" + base_url);

        TransferResponse response = webClient.post()
                .uri("/balance/transfer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransferResponse.class)
                .block();

        System.out.println("AccountClient.transfer: response=" + response);
        return response;
    }
}
