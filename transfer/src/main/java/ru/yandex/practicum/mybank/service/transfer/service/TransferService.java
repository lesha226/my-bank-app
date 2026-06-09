package ru.yandex.practicum.mybank.service.transfer.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.transfer.client.AccountsClient;
import ru.yandex.practicum.mybank.service.transfer.client.dto.AccountsTransferRequest;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.model.TransferRequest;

@Service
public class TransferService {

    private final AccountsClient accountsClient;

    public TransferService(AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    public TransferResponse transfer(String login, TransferRequest request) {
        System.out.println("TransferService.transfer login=" + login + ", request=" + request);
        if (login == null || Strings.isBlank(login) || request == null || Strings.isBlank(request.toLogin())) {
            throw new IllegalArgumentException();
        }

        AccountsTransferRequest AccountsTransferRequest = new AccountsTransferRequest(
                login, request.toLogin(), request.amount());

        TransferResponse response = accountsClient.transfer(AccountsTransferRequest);

        System.out.println("TransferService.transfer request=" + request);
        return response;
    }

}
