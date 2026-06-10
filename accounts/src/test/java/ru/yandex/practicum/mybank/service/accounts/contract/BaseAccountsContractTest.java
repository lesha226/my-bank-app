package ru.yandex.practicum.mybank.service.accounts.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.mybank.service.accounts.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.accounts.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybank.service.accounts.dto.*;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.BalanceResponse;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.accounts.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.accounts.service.AccountsService;
import ru.yandex.practicum.mybank.service.accounts.service.BalanceService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, ServiceClientTestConfig.class/*, MockBalanceServiceConfig.class*/})
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class BaseAccountsContractTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AccountsService accountsService;

    @MockitoBean
    BalanceService balanceService;

    private static final AccountFullDataDto TEST_ACCOUNT = new AccountFullDataDto(
            "test-user",
            "Test user",
            LocalDate.ofYearDay(2001, 1),
            123,
            List.of(
                    new AccountDto("test-user1", "Test user1"),
                    new AccountDto("test-user2", "Test user2")
            )
    );


    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        // get /accoiunts/{login}
        when(accountsService.getAccount("test-user")).thenReturn(TEST_ACCOUNT);
        when(accountsService.getAccount("non-existent-user")).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with login: non-existent-user"));

        // path /accoiunts/{login}
        when(accountsService.updateAccount(eq("test-user"), any())).thenReturn(TEST_ACCOUNT);
        when(accountsService.updateAccount(eq("non-existent-user"), any())).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with login: non-existent-user"));

        // post /balance/deposit
        when(balanceService.deposit(new DepositRequest("test-user", 10)))
                .thenReturn(new BalanceResponse("Внесение выполнено: 10 на счёт test-user"));
        when(balanceService.deposit(new DepositRequest("non-existent-user", 10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

        // post /balance/withdraw
        when(balanceService.withdraw(new WithdrawRequest("test-user", 10)))
                .thenReturn(new BalanceResponse("Снятие выполнено: 10 со счёта test-user"));
        when(balanceService.withdraw(new WithdrawRequest("non-existent-user", 10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

        // post /balance/transfer
        when(balanceService.transfer(new TransferRequest("test-user", "test-user2",10)))
                .thenReturn(new BalanceResponse("Перевод выполнен: 10 со счёта test-user на счёт test-user2"));
        when(balanceService.transfer(new TransferRequest("non-existent-user", "",10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

    }
}
