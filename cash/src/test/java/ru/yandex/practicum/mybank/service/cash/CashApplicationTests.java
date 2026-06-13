package ru.yandex.practicum.mybank.service.cash;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.mybank.service.cash.config.ServiceClientTestConfig;
import ru.yandex.practicum.mybank.service.cash.config.JwtTestConfig;

//@SpringBootTest
//@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, ServiceClientTestConfig.class})
class CashApplicationTests {

	@Test
	void contextLoads() {
	}

}
