package ru.yandex.practicum.mybankfront;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybankfront.config.ServiceClientTestConfig;

@SpringBootTest
@ActiveProfiles("contract-test")
@Import(ServiceClientTestConfig.class)
class MyBankFrontAppApplicationTest {

    @Test
    void contextLoads() {
    }
}