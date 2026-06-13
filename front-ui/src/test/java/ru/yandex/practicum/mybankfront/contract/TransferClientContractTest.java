package ru.yandex.practicum.mybankfront.contract;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum.mybank.service:transfer:+:stubs:8087",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@Import(ServiceClientTestConfig.class)
public class TransferClientContractTest {

    @Autowired
    TransferClient transferClient;

    @Test
    void transfer() {
        TransferRequest request = new TransferRequest(10, "test-user2");
        ServiceResponse response = transferClient.transfer("test-user", request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
    }
}