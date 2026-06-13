package ru.yandex.practicum.mybank.service.account.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.account.model.Account;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    String login = "user";
    Account account = new Account(null, "user", "username", LocalDate.ofYearDay(2001,1), 123);

    @Test
    void account() {
        accountRepository.save(account);

        Account result = accountRepository.findByLogin("user").get();
        List<Account> emptyList = accountRepository.findAllByLoginNot("user");
        List<Account> all = accountRepository.findAllByLoginNot("other-user");

        assertEquals(result, account);
        assertEquals(emptyList, List.of());
        assertEquals(all.getFirst(), account);
    }

}