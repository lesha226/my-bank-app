package ru.yandex.practicum.mybank.service.cash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.mybank.service.cash.config.AccountsClientTestConfig;
import ru.yandex.practicum.mybank.service.cash.config.JwtTestConfig;

//@SpringBootTest
//@ActiveProfiles("contract-test")
@Import({JwtTestConfig.class, AccountsClientTestConfig.class})
class CashApplicationTests {

	@Test
	void contextLoads() {
	}

}
