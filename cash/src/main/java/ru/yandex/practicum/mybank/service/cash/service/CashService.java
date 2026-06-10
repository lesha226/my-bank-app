package ru.yandex.practicum.mybank.service.cash.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.cash.client.AccountsClient;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsDepositRequest;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsWithdrawRequest;
import ru.yandex.practicum.mybank.service.cash.model.CashActionRequest;

@Service
public class CashService {

    private final AccountsClient client;

    public CashService(AccountsClient client) {
        this.client = client;
    }

    public AccountsResponse action(String login, CashActionRequest request) {
        System.out.println("CashService.action: login=" + login + ", request=" + request);

        if (login == null || Strings.isBlank(login) || request == null || request.action() == null || request.value() <= 0) {
            throw new IllegalArgumentException();
        }

        AccountsResponse response = null;
        switch (request.action()) {
            case PUT -> {
                AccountsDepositRequest depositRequest = new AccountsDepositRequest(login, request.value());
                response = client.deposit(depositRequest);
            }
            case GET -> {
                AccountsWithdrawRequest withdrawRequest = new AccountsWithdrawRequest(login, request.value());
                response = client.withdraw(withdrawRequest);
            }
            default -> throw new IllegalArgumentException();
        };

        System.out.println("CashService.action: response=" + response);
        return response;
    }

}
