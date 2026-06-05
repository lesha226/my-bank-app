package ru.yandex.practicum.mybankfront.contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.client.AccountsClient;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-contract")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:accounts:+:stubs:8085",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class AccountsClientContractTest {

    @Autowired
    AccountsClient accountsClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAccount() {
        AccountFullDataDto response = accountsClient.getAccount("test-user");

        assertNotNull(response);
        assertEquals("test-user", response.login());
        assertEquals("Test user", response.name());
        assertEquals(LocalDate.of(2001,1,1), response.birthdate());
        assertEquals(123, response.balanceAmount());
    }

    @Test
    void updateAccount() {
        EditAccountRequest request = new EditAccountRequest("Test user", LocalDate.of(2001,1,1));
        AccountFullDataDto response = accountsClient.updateAccount("test-user", request);

        assertNotNull(response);
        assertEquals("test-user", response.login());
        assertEquals("Test user", response.name());
        assertEquals(LocalDate.of(2001,1,1), response.birthdate());
        assertEquals(123, response.balanceAmount());
    }
}