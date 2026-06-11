package ru.yandex.practicum.mybank.service.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.account.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.account.config.ServiceClientTestConfig;

@SpringBootTest
@ActiveProfiles("contract-test")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
@Import({JwtTestConfig.class, ServiceClientTestConfig.class})
class AccountApplicationTest {

    @Test
    void contextLoads() {
    }

}