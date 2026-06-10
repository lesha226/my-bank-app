package ru.yandex.practicum.mybank.service.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.accounts.config.JwtTestConfig;
import ru.yandex.practicum.mybank.service.accounts.config.ServiceClientTestConfig;

@SpringBootTest
@ActiveProfiles("contract-test")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
@Import({JwtTestConfig.class, ServiceClientTestConfig.class})
class AccountsApplicationTest {

    @Test
    void contextLoads() {
    }

}