package ru.yandex.practicum.mybankfront.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.CashAction;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:cash:+:stubs:8086",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import(ServiceClientTestConfig.class)
public class CashClientContractTest {

    @Autowired
    CashClient cashClient;

    @Test
    void action() {
        EditCashRequest request = new EditCashRequest(123, CashAction.PUT);
        cashClient.action("test-user", request);
    }
}
