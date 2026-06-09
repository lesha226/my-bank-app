package ru.yandex.practicum.mybank.service.accounts.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.accounts.model.Account;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
class AccountsRepositoryTest {

    @Autowired
    AccountsRepository accountsRepository;

    @BeforeEach
    void setUp() {
        accountsRepository.deleteAll();
    }

    String login = "user";
    Account account = new Account(null, "user", "username", LocalDate.ofYearDay(2001,1), 123);

    @Test
    void account() {
        accountsRepository.save(account);

        Account result = accountsRepository.findByLogin("user").get();
        List<Account> emptyList = accountsRepository.findAllByLoginNot("user");
        List<Account> all = accountsRepository.findAllByLoginNot("other-user");


        assertEquals(result, account);
        assertEquals(emptyList, List.of());
        assertEquals(all.getFirst(), account);
    }

}