package ru.yandex.practicum.mybank.service.transfer.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mybank.service.transfer.config.ServiceClientBuilderTestConfig;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.transfer.model.TransferRequest;
import ru.yandex.practicum.mybank.service.transfer.service.TransferService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, ServiceClientBuilderTestConfig.class})
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class BaseContractTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TransferService transferService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        String login = "test-user";
        TransferRequest request= new TransferRequest("test-user2", 10);
        TransferResponse response = new TransferResponse("message");
        when(transferService.transfer(login, request)).thenReturn(response);

    }
}
