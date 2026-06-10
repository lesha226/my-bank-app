package ru.yandex.practicum.mybank.service.cash.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.cash.client.AccountsClient;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsDepositRequest;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsWithdrawRequest;
import ru.yandex.practicum.mybank.service.cash.model.CashActionRequest;
import ru.yandex.practicum.mybank.service.cash.outbox.Outbox;
import ru.yandex.practicum.mybank.service.cash.outbox.OutboxService;

import java.time.LocalDateTime;

@Service
public class CashService {

    private final AccountsClient client;
    private final OutboxService outboxService;
    //private final Consumer<AccountsDepositRequest> depositNotificationSender;
    //private final Consumer<AccountsWithdrawRequest> withdrawNotificationSender;

    public CashService(AccountsClient client, OutboxService outboxService) {
        this.client = client;
        this.outboxService = outboxService;
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
                //depositNotificationSender.accept(depositRequest);
            }
            case GET -> {
                AccountsWithdrawRequest withdrawRequest = new AccountsWithdrawRequest(login, request.value());
                response = client.withdraw(withdrawRequest);
                //withdrawNotificationSender.accept(withdrawRequest);
            }
            default -> throw new IllegalArgumentException();
        };

        asyncNotify(login, request, response);

        System.out.println("CashService.action: response=" + response);
        return response;
    }

    private void asyncNotify(String login, CashActionRequest request, AccountsResponse response) {
        System.out.println("CashService.asyncNotify: login=" + login + ", request=" + request + ", response=" + response);
        try {
            CashActionOutboxBody cashActionOutboxBody = new CashActionOutboxBody(request, response);
            String body = cashActionOutboxBody.toString();//objectMapper.writeValueAsString(cashActionBody);  // TODO : switch to objectMapper
            Outbox outbox = new Outbox(null, "cash.action", login, LocalDateTime.now(), body);

            outboxService.asyncNotify(outbox);
        } catch (Exception e) {
            System.out.println("ERROR CashService.asyncNotify: " + e.getMessage());
        }
    }

    private static record CashActionOutboxBody(CashActionRequest request, AccountsResponse response) {};

}
