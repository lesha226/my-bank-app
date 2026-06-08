package ru.yandex.practicum.mybank.service.accounts.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountDto;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.model.Account;
import ru.yandex.practicum.mybank.service.accounts.repository.AccountsRepository;
import ru.yandex.practicum.mybank.service.accounts.service.mapper.AccountMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {

    @InjectMocks
    AccountsService accountsService;

    @Mock
    AccountsRepository accountsRepository;

    private final static AccountFullDataDto dto = new AccountFullDataDto(
            "user",
            "fullname",
            LocalDate.of(2001, 1, 1),
            123,
            List.of(
                    new AccountDto("user2", "fullname2"),
                    new AccountDto("user3", "fullname3")
            )
    );
    private final static String login = "user";
    private final static Account account = new Account(1L, "user", "fullname", LocalDate.ofYearDay(2001, 1), 123);
    private final static List<Account> accounts = List.of(
            new Account(2L, "user2", "fullname2", LocalDate.ofYearDay(2001, 2), 200),
            new Account(3L, "user3", "fullname3", LocalDate.ofYearDay(2001, 3), 300)
    );

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAccount() {

        when(accountsRepository.findByLogin(login)).thenReturn(Optional.of(account));
        when(accountsRepository.findAllByLoginNot(login)).thenReturn(accounts);

        AccountFullDataDto result = accountsService.getAccount(login);

        assertEquals(result, dto);

        verify(accountsRepository).findByLogin(login);
        verify(accountsRepository).findAllByLoginNot(login);
    }

    @Test
    void updateAccount() {
        UpdateAccountRequest request = new UpdateAccountRequest("FullNameUpd", LocalDate.ofYearDay(2001, 10));
        Account updAccount = new Account(1L, "user", "FullNameUpd", LocalDate.ofYearDay(2001, 10), 123);
        AccountFullDataDto updDto = new AccountFullDataDto(
                "user","FullNameUpd",LocalDate.of(2001, 1, 10), 123,
                dto.contacts());

        when(accountsRepository.findByLogin(login)).thenReturn(Optional.of(account));
        when(accountsRepository.save(any())).thenReturn(updAccount);

        when(accountsRepository.findAllByLoginNot(login)).thenReturn(accounts);

        AccountFullDataDto result = accountsService.updateAccount(login, request);

        assertEquals(result, updDto);

        verify(accountsRepository).findByLogin(login);
        verify(accountsRepository).save(any());
        verify(accountsRepository).findAllByLoginNot(login);
    }
}