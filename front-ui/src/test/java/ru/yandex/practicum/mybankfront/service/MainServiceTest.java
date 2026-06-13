package ru.yandex.practicum.mybankfront.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.mybankfront.client.AccountClient;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.controller.dto.*;
import ru.yandex.practicum.mybankfront.client.dto.AccountDetailResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.mybankfront.config.SecurityTestConfig.TEST_USER;
import static ru.yandex.practicum.mybankfront.config.SecurityTestConfig.TEST_USER_USERNAME;

@ExtendWith(MockitoExtension.class)
class MainServiceTest {

    @InjectMocks
    MainService mainService;

    @Mock
    AccountClient accountsClient;

    @Mock
    CashClient cashClient;

    @Mock
    TransferClient transferClient;

    private static final List<AccountDto> ACCOUNTS = List.of(
            new AccountDto("login1", "name1"),
            new AccountDto("login2", "name2")
    );
    private final static AccountDetailResponse ACCOUNT_FULL_DATA_DTO = new AccountDetailResponse(
            "login", "name", LocalDate.of(2001, 1, 1), 123, ACCOUNTS);
    private final static ServiceResponse SERVICE_RESPONSE = new ServiceResponse("message");
    private final static ExecutionStatusResponse EXECUTION_STATUS_RESPONSE = new ExecutionStatusResponse(List.of("test error"), "test info");

    @Test
    void getAccountReturnAccountDetail() {

        when(accountsClient.getAccountDetail(TEST_USER_USERNAME)).thenReturn(ACCOUNT_FULL_DATA_DTO);

        AccountResponse response = mainService.getAccountDetail(TEST_USER, EXECUTION_STATUS_RESPONSE);

        assertNotNull(response);
        assertEquals(response.name(), ACCOUNT_FULL_DATA_DTO.name());
        assertEquals(response.birthdate(), ACCOUNT_FULL_DATA_DTO.birthdate().format(DateTimeFormatter.ISO_DATE));
        assertEquals(response.sum(), ACCOUNT_FULL_DATA_DTO.balanceAmount());
        assertEquals(response.accounts(), ACCOUNT_FULL_DATA_DTO.contacts());
        assertEquals(response.errors(), EXECUTION_STATUS_RESPONSE.errors());
        assertEquals(response.info(), EXECUTION_STATUS_RESPONSE.info());

        verify(accountsClient).getAccountDetail(TEST_USER_USERNAME);
    }


    @Test
    void getAccountDetailReturnError() {
        String errorMessage = "errorMessage";
        List<String> errors = new ArrayList<>(EXECUTION_STATUS_RESPONSE.errors());
        errors.add(errorMessage);

        when(accountsClient.getAccountDetail(TEST_USER_USERNAME)).thenThrow(new RuntimeException(errorMessage));

        AccountResponse response = mainService.getAccountDetail(TEST_USER, EXECUTION_STATUS_RESPONSE);

        assertNotNull(response);
        assertNull(response.name());
        assertNull(response.birthdate());
        assertNull(response.sum());
        assertEquals(response.accounts(), List.of());
        assertEquals(response.errors(), errors);
        assertEquals(response.info(), EXECUTION_STATUS_RESPONSE.info());

        verify(accountsClient).getAccountDetail(TEST_USER_USERNAME);
    }

    @Test
    void editAccountReturnInfo() {
        EditAccountRequest request = new EditAccountRequest("name", LocalDate.ofYearDay(2001,1));
        when(accountsClient.updateAccount(TEST_USER_USERNAME, request)).thenReturn(SERVICE_RESPONSE);

        ExecutionStatusResponse response = mainService.editAccount(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of());
        assertNotNull(response.info());
        assertFalse(response.info().isBlank());

        verify(accountsClient).updateAccount(TEST_USER_USERNAME, request);
    }

    @Test
    void editAccountReturnError() {
        EditAccountRequest request = new EditAccountRequest("name", LocalDate.ofYearDay(2001,1));
        doThrow(new RuntimeException("error")).when(accountsClient).updateAccount(TEST_USER_USERNAME, request);

        ExecutionStatusResponse response = mainService.editAccount(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of("error"));
        assertNull(response.info());

        verify(accountsClient).updateAccount(TEST_USER_USERNAME, request);
    }

    @Test
    void editCashReturnInfo() {
        EditCashRequest request = new EditCashRequest(123, CashAction.PUT);
        when(cashClient.action(TEST_USER_USERNAME, request)).thenReturn(new ServiceResponse("message"));

        ExecutionStatusResponse response = mainService.editCash(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of());
        assertNotNull(response.info());
        assertFalse(response.info().isBlank());

        verify(cashClient).action(TEST_USER_USERNAME, request);
    }

    @Test
    void editCashReturnError() {
        EditCashRequest request = new EditCashRequest(123, CashAction.PUT);
        doThrow(new RuntimeException("error")).when(cashClient).action(TEST_USER_USERNAME, request);

        ExecutionStatusResponse response = mainService.editCash(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of("error"));
        assertNull(response.info());

        verify(cashClient).action(TEST_USER_USERNAME, request);
    }

    @Test
    void transferReturnInfo() {
        TransferRequest request = new TransferRequest(123, "login1");
        when(transferClient.transfer(TEST_USER_USERNAME, request)).thenReturn(new ServiceResponse("message"));

        ExecutionStatusResponse response = mainService.transfer(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of());
        assertNotNull(response.info());
        assertFalse(response.info().isBlank());

        verify(transferClient).transfer(TEST_USER_USERNAME, request);
    }

    @Test
    void transferReturnError() {
        TransferRequest request = new TransferRequest(123, "login1");
        doThrow(new RuntimeException("error")).when(transferClient).transfer(TEST_USER_USERNAME, request);

        ExecutionStatusResponse response = mainService.transfer(TEST_USER, request);

        assertNotNull(response);
        assertEquals(response.errors(), List.of("error"));
        assertNull(response.info());

        verify(transferClient).transfer(TEST_USER_USERNAME, request);
    }
}