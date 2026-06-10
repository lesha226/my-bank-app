package ru.yandex.practicum.mybank.service.cash.contract;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.cash.client.AccountsClient;
import ru.yandex.practicum.mybank.service.cash.config.AccountsClientTestConfig;
import ru.yandex.practicum.mybank.service.cash.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsDepositRequest;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsWithdrawRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:accounts:0.0.1-SNAPSHOT:stubs:8085",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import({JwtTestConfig.class, AccountsClientTestConfig.class})
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class AccountsClientContractTest {

    @Autowired
    AccountsClient accountsClient;

    @Test
    void deposit() {
        AccountsDepositRequest request = new AccountsDepositRequest("test-user", 10);
        AccountsResponse response = accountsClient.deposit(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
    }

    @Test
    void withdraw() {
        AccountsWithdrawRequest request = new AccountsWithdrawRequest("test-user", 10);
        AccountsResponse response = accountsClient.withdraw(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
    }
}
