package ru.yandex.practicum.mybank.service.transfer.contract;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.transfer.client.AccountsClient;
import ru.yandex.practicum.mybank.service.transfer.client.dto.AccountsTransferRequest;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.config.WebClientBuilderTestConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:accounts:0.0.1-SNAPSHOT:stubs:8085",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import(WebClientBuilderTestConfig.class)
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class AccountsClientContractTest {

    @Autowired
    AccountsClient accountsClient;

    @Test
    void transfer() {
        //DepositRequest request = new DepositRequest("test-user", 10);
        AccountsTransferRequest request = new AccountsTransferRequest("test-user", "test-user2", 10);
        TransferResponse response = accountsClient.transfer(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
    }
}
