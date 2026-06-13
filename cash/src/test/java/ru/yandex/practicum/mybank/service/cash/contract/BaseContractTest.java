package ru.yandex.practicum.mybank.service.cash.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mybank.service.cash.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybank.service.cash.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.service.CashService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, ServiceClientTestConfig.class})
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
public class BaseContractTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CashService cashService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        AccountsResponse response = new AccountsResponse("message");
        when(cashService.action(eq("test-user"), any())).thenReturn(response);

    }

    @Test
    void contextLoads() {
    }
}
