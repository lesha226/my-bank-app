package ru.yandex.practicum.mybank.service.notification.contract;

//import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
public class BaseContractTest {

    @Autowired
    MockMvc mockMvc;

    //@MockitoBean
    //TamplateService tamplateService;

    @BeforeEach
    public void setup() {
        //RestAssuredMockMvc.mockMvc(mockMvc);

    }
}
