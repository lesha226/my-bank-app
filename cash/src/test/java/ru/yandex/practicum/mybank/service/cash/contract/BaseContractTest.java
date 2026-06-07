package ru.yandex.practicum.mybank.service.cash.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.mybank.service.cash.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.cash.service.CashService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
@Import(JwtTestConfig.class)
public class BaseContractTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CashService cashService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        doNothing().when(cashService).action(eq("test-user"), any(), any());

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with login: non-existent-user"))
                .when(cashService).action(eq("non-existent-user"), any(), any());
    }
}
