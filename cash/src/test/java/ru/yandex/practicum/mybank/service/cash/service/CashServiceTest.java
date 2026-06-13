package ru.yandex.practicum.mybank.service.cash.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.mybank.service.cash.client.AccountsClient;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsDepositRequest;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsWithdrawRequest;
import ru.yandex.practicum.mybank.service.cash.model.CashAction;
import ru.yandex.practicum.mybank.service.cash.model.CashActionRequest;
import ru.yandex.practicum.mybank.service.cash.outbox.OutboxService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashServiceTest {

    @InjectMocks
    CashService service;

    @Mock
    AccountsClient client;

    @Mock
    OutboxService outboxService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void action_put() {
        String login = "user";
        int amount = 10;
        CashActionRequest request = new CashActionRequest(10, CashAction.PUT);
        AccountsResponse clientResponse = new AccountsResponse("message");
        AccountsDepositRequest clientRequest = new AccountsDepositRequest(login, amount);

        when(client.deposit(clientRequest)).thenReturn(clientResponse);

        AccountsResponse response = service.action(login, request);

        assertEquals(response, clientResponse);

        verify(client).deposit(clientRequest);
    }

    @Test
    void action_get() {
        String login = "user";
        int amount = 10;
        CashActionRequest request = new CashActionRequest(10, CashAction.GET);
        AccountsResponse clientResponse = new AccountsResponse("message");
        AccountsWithdrawRequest clientRequest = new AccountsWithdrawRequest(login, amount);

        when(client.withdraw(clientRequest)).thenReturn(clientResponse);

        AccountsResponse response = service.action(login, request);

        assertEquals(response, clientResponse);

        verify(client).withdraw(clientRequest);
    }
}