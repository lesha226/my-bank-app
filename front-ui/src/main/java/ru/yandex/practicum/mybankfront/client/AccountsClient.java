package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

@Component
public class AccountsClient {

    private final String base_url;
    //private final RestClient restClient;
    private final WebClient webClient;

    public AccountsClient(
            @Value("${bank.service.accounts.base-url}") String base_url,
            //RestClient.Builder builder
            WebClient.Builder webClientBuilder
    ) {
        this.base_url = base_url;
        //this.restClient = builder.baseUrl(base_url).build();
        this.webClient = webClientBuilder.baseUrl(base_url).build();
    }

    /*public AccountFullDataDto getAccount(String login) {
        System.out.println("AccountsClient.getAccount: login=" + login + ", base_url=" + base_url);

        return restClient.get()
                .uri("/accounts/{login}", login)
                .retrieve()
                .body(AccountFullDataDto.class);
    }

    public AccountFullDataDto updateAccount(String login, EditAccountRequest request) {
        System.out.println("AccountsClient.getAccount: updateAccount=" + login + ", request=" + request + ", base_url=" + base_url);

        return restClient.patch()
                .uri("/accounts/{login}", login)
                .body(request)
                .retrieve()
                .body(AccountFullDataDto.class);
    }*/

    public AccountFullDataDto getAccount(String login) {
        System.out.println("AccountsClient.getAccount: login=" + login + ", base_url=" + base_url);

        return webClient.get()
                .uri("/accounts/{login}", login)
                .retrieve()
                .bodyToMono(AccountFullDataDto.class)
                .block();
    }

    public AccountFullDataDto updateAccount(String login, EditAccountRequest request) {
        System.out.println("AccountsClient.getAccount: updateAccount=" + login + ", request=" + request + ", base_url=" + base_url);

        return webClient.patch()
                .uri("/accounts/{login}", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AccountFullDataDto.class)
                .block();
    }

}
