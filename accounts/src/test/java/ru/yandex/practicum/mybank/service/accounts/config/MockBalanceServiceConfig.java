package ru.yandex.practicum.mybank.service.accounts.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.yandex.practicum.mybank.service.accounts.service.BalanceService;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class MockBalanceServiceConfig {

    @Bean
    @Primary
    public BalanceService balanceService() {
        return mock(BalanceService.class);
    }

}
