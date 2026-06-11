package ru.yandex.practicum.mybank.service.account.contract;

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
import ru.yandex.practicum.mybank.service.account.dto.AccountLiteResponse;
import ru.yandex.practicum.mybank.service.account.dto.AccountDetailResponse;
import ru.yandex.practicum.mybank.service.account.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.account.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.account.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.account.service.AccountService;
import ru.yandex.practicum.mybank.service.account.service.BalanceService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, ServiceClientTestConfig.class})
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class BaseAccountsContractTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AccountService accountService;

    @MockitoBean
    BalanceService balanceService;

    private static final AccountDetailResponse TEST_ACCOUNT = new AccountDetailResponse(
            "user",
            "name",
            LocalDate.ofYearDay(2001, 1),
            100,
            List.of(
                    new AccountLiteResponse("user2", "fullName2"),
                    new AccountLiteResponse("user3", "fullName3")
            )
    );

    private static final ServiceResponse SERVICE_RESPONSE = new ServiceResponse("message");


    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        // get /accoiunt/{login}/detail
        when(accountService.getAccountDetail("user")).thenReturn(TEST_ACCOUNT);
        when(accountService.getAccountDetail("nonUser")).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "error"));

        // path /accoiunts/{login}
        when(accountService.updateAccount(eq("user"), any())).thenReturn(new ServiceResponse("message"));
        when(accountService.updateAccount(eq("nonUser"), any())).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "error"));

        // post /balance/deposit
        when(balanceService.deposit(new DepositRequest("test-user", 10)))
                .thenReturn(new ServiceResponse("Внесение выполнено: 10 на счёт test-user"));
        when(balanceService.deposit(new DepositRequest("non-existent-user", 10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

        // post /balance/withdraw
        when(balanceService.withdraw(new WithdrawRequest("test-user", 10)))
                .thenReturn(new ServiceResponse("Снятие выполнено: 10 со счёта test-user"));
        when(balanceService.withdraw(new WithdrawRequest("non-existent-user", 10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

        // post /balance/transfer
        when(balanceService.transfer(new TransferRequest("test-user", "test-user2",10)))
                .thenReturn(new ServiceResponse("Перевод выполнен: 10 со счёта test-user на счёт test-user2"));
        when(balanceService.transfer(new TransferRequest("non-existent-user", "",10)))
                .thenThrow(new LoginNotFoundException("non-existent-user"));

    }
}
