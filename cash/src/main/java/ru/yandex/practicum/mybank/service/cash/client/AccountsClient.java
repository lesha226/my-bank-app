package ru.yandex.practicum.mybank.service.cash.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsDepositRequest;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsWithdrawRequest;

@Component
public class AccountsClient {

    private final String baseUrl;
    private final WebClient webClient;

    public AccountsClient(@Value("${bank.service.accounts.base-url}") String baseUrl, WebClient.Builder serviceClientBuilder) {
        this.baseUrl = baseUrl;
        this.webClient = serviceClientBuilder.baseUrl(baseUrl).build();
    }

    public AccountsResponse deposit(AccountsDepositRequest request) {
        System.out.println("AccountsClient.deposit: request=" + request);

        AccountsResponse response = webClient.post()
                .uri("/balance/deposit")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AccountsResponse.class)
                .block();

        System.out.println("AccountsClient.deposit: response=" + response);
        return response;
    }

    public AccountsResponse withdraw(AccountsWithdrawRequest request) {
        System.out.println("AccountsClient.withdraw: request=" + request);

        AccountsResponse response = webClient.post()
                .uri("/balance/withdraw")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AccountsResponse.class)
                .block();

        System.out.println("AccountsClient.withdraw: response=" + response);
        return response;
    }
}
