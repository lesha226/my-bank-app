package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

@Component
public class AccountsClient {

    private final String base_url;
    private final RestClient restClient;

    public AccountsClient(@Value("${bank.accounts-service.base-url}") String base_url) {
        this.base_url = base_url;
        this.restClient = RestClient.builder()
                .baseUrl(base_url)
                .build();
    }

    public AccountFullDataDto getAccount(String login) {
        System.out.println("AccountsClient.getAccount: login=" + login + ", base_url=" + base_url);

        return restClient.get()
                .uri("/accounts/{login}", login)
                .retrieve()
                .body(AccountFullDataDto.class);
    }

    public AccountFullDataDto updateAccount(String login, EditAccountRequest request) {
        return restClient.patch()
                .uri("/accounts/{login}", login)
                .body(request)
                .retrieve()
                .body(AccountFullDataDto.class);
    }


}
