package ru.yandex.practicum.mybankfront.contract;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.practicum.mybankfront.config.ClientTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.CashAction;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-contract")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:transfer:+:stubs:8087",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import(ClientTestConfig.class)
public class TransferClientContractTest {

    @Autowired
    TransferClient transferClient;

    @Test
    void transfer() {
        TransferRequest request = new TransferRequest(123, "test-user2");
        transferClient.transfer("test-user", request);
    }
}