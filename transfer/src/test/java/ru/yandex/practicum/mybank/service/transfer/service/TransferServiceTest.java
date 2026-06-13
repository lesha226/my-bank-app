package ru.yandex.practicum.mybank.service.transfer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.mybank.service.transfer.client.AccountsClient;
import ru.yandex.practicum.mybank.service.transfer.client.dto.AccountsTransferRequest;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.model.TransferRequest;
import ru.yandex.practicum.mybank.service.transfer.outbox.OutboxService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @InjectMocks
    TransferService transferService;

    @Mock
    AccountsClient accountsClient;

    @Mock
    OutboxService outboxService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void transfer() {
        String fromLogin = "fromLogin";
        String toLogin = "toLogin";
        int amount = 10;
        TransferRequest transferRequest = new TransferRequest(toLogin, amount);
        AccountsTransferRequest AccountsTransferRequest = new AccountsTransferRequest(fromLogin, toLogin, amount);
        TransferResponse response = new TransferResponse("message");
        when(accountsClient.transfer(AccountsTransferRequest)).thenReturn(response);

        TransferResponse result = transferService.transfer(fromLogin, transferRequest);

        assertEquals(result, response);

        verify(accountsClient).transfer(AccountsTransferRequest);
    }
}