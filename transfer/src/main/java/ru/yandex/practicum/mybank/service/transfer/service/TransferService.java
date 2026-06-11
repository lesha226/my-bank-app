package ru.yandex.practicum.mybank.service.transfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.transfer.client.AccountsClient;
import ru.yandex.practicum.mybank.service.transfer.client.dto.AccountsTransferRequest;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.model.TransferRequest;
import ru.yandex.practicum.mybank.service.transfer.outbox.Outbox;
import ru.yandex.practicum.mybank.service.transfer.outbox.OutboxService;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final AccountsClient accountsClient;
    private final OutboxService outboxService;
    private final ObjectMapper objectMapper;


    public TransferService(AccountsClient accountsClient, OutboxService outboxService, ObjectMapper objectMapper) {
        this.accountsClient = accountsClient;
        this.outboxService = outboxService;
        this.objectMapper = objectMapper;
    }

    public TransferResponse transfer(String login, TransferRequest request) {
        System.out.println("TransferService.transfer login=" + login + ", request=" + request);
        if (login == null || Strings.isBlank(login) || request == null || Strings.isBlank(request.toLogin())) {
            throw new IllegalArgumentException();
        }

        AccountsTransferRequest AccountsTransferRequest = new AccountsTransferRequest(
                login, request.toLogin(), request.amount());

        TransferResponse response = accountsClient.transfer(AccountsTransferRequest);

        asyncNotify(login, request, response);

        System.out.println("TransferService.transfer request=" + request);
        return response;
    }

    private void asyncNotify(String login, TransferRequest request, TransferResponse response) {
        System.out.println("TransferService.asyncNotify: login=" + login + ", request=" + request + ", response=" + response);
        try {
            TransferOutboxBody transferOutboxBody = new TransferOutboxBody(request, response);
            String body = objectMapper.writeValueAsString(transferOutboxBody);
            Outbox outbox = new Outbox(null, "transfer.transfer", login, LocalDateTime.now(), body);

            outboxService.asyncNotify(outbox);
        } catch (Exception e) {
            System.out.println("ERROR TransferService.asyncNotify: " + e.getMessage());
        }
    }

    private static record TransferOutboxBody(TransferRequest request, TransferResponse response) {};

}
