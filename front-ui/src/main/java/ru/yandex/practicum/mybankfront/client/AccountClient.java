package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.client.dto.AccountDetailResponse;

@Component
public class AccountClient {

    private final String base_url;
    private final WebClient webClient;

    public AccountClient(
            @Value("${bank.service.account.base-url}") String base_url,
            WebClient.Builder serviceClientBuilder
    ) {
        this.base_url = base_url;
        this.webClient = serviceClientBuilder.baseUrl(base_url).build();
    }

    public AccountDetailResponse getAccountDetail(String login) {
        System.out.println("AccountClient.getAccountDetail: login=" + login + ", base_url=" + base_url);

        return webClient.get()
                .uri("/account/{login}/detail", login)
                .retrieve()
                .bodyToMono(AccountDetailResponse.class)
                .block();
    }

    public ServiceResponse updateAccount(String login, EditAccountRequest request) {
        System.out.println("AccountClient.updateAccount: login=" + login + ", request=" + request + ", base_url=" + base_url);

        return webClient.patch()
                .uri("/account/{login}", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .block();
    }

}
