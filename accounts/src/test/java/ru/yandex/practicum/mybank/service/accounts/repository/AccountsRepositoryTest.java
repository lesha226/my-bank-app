package ru.yandex.practicum.mybank.service.accounts.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.yandex.practicum.mybank.service.accounts.model.Account;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class AccountsRepositoryTest {

    @Autowired
    AccountsRepository accountsRepository;

    @BeforeEach
    void setUp() {
        accountsRepository.deleteAll();
    }

    @Test
    void account() {
        Account account = new Account(null, "user", "username", LocalDate.ofYearDay(2001,1), 123);
        accountsRepository.save(account);

        Account result = accountsRepository.findByLogin("user").get();
        List<Account> emptyList = accountsRepository.findAllByLoginNot("user");
        List<Account> all = accountsRepository.findAllByLoginNot("asdf");


        assertEquals(result, account);
        assertEquals(emptyList, List.of());
        assertEquals(all.getFirst(), account);
    }

}