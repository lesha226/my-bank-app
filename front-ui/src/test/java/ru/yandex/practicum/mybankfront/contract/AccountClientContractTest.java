package ru.yandex.practicum.mybankfront.contract;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.client.AccountClient;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.client.dto.AccountDetailResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:account:0.0.1-SNAPSHOT:stubs:8085",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import(ServiceClientTestConfig.class)
class AccountClientContractTest {

    @Autowired
    AccountClient accountsClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAccountDetail() {
        AccountDetailResponse response = accountsClient.getAccountDetail("user");

        assertNotNull(response);
        assertEquals("user", response.login());
        assertEquals("name", response.name());
        assertEquals(LocalDate.ofYearDay(2001,1), response.birthdate());
        assertEquals(100, response.balanceAmount());
    }

    @Test
    void updateAccount() {
        EditAccountRequest request = new EditAccountRequest("fullName", LocalDate.of(2001,1,1));
        ServiceResponse result = accountsClient.updateAccount("user", request);

        assertNotNull(result);
        assertFalse(Strings.isBlank(result.info()));
    }
}