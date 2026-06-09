package ru.yandex.practicum.mybank.service.accounts.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.BalanceResponse;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.accounts.exception.InsufficientFundsException;
import ru.yandex.practicum.mybank.service.accounts.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.accounts.model.Account;
import ru.yandex.practicum.mybank.service.accounts.repository.AccountsRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"spring.cloud.config.enabled=false"})
class BalanceServiceTest {

    @InjectMocks
    BalanceService service;

    @Mock
    AccountsRepository repository;

    private Account fromAccount;
    private Account fromUpdAccount;
    private Account toAccount;
    private Account toUpdAccount;

    @BeforeEach
    void setUp() {
        fromAccount    = new Account(1L, "from-login", "Fullname2", LocalDate.ofYearDay(2001, 2), 200);
        fromUpdAccount = new Account(1L, "from-login", "Fullname2", LocalDate.ofYearDay(2001, 2), 190);
        toAccount    = new Account(2L, "to-login", "Fullname3", LocalDate.ofYearDay(2001, 3), 300);
        toUpdAccount = new Account(2L, "to-login", "Fullname3", LocalDate.ofYearDay(2001, 3), 310);
    }

    @Test
    void deposit_ok() {
        DepositRequest request = new DepositRequest("to-login", 10);
        when(repository.findByLogin("to-login")).thenReturn(Optional.of(toAccount));
        when(repository.save(toUpdAccount)).thenReturn(toUpdAccount);

        BalanceResponse response = service.deposit(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
        verify(repository).findByLogin("to-login");
        verify(repository).save(toUpdAccount);
    }

    @Test
    void deposit_LoginNotFound() {
        DepositRequest request = new DepositRequest("to-login", 10);
        when(repository.findByLogin("to-login")).thenReturn(Optional.empty());

        assertThrows(LoginNotFoundException.class, () ->  service.deposit(request));

        verify(repository).findByLogin("to-login");
        verify(repository, never()).save(any());
    }

    @Test
    void withdraw_ok() {
        WithdrawRequest request = new WithdrawRequest("from-login", 10);
        when(repository.findByLogin("from-login")).thenReturn(Optional.of(fromAccount));
        when(repository.save(fromUpdAccount)).thenReturn(any());

        BalanceResponse response = service.withdraw(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
        verify(repository).findByLogin("from-login");
        verify(repository).save(fromUpdAccount);
    }

    @Test
    void withdraw_LoginNotFound() {
        WithdrawRequest request = new WithdrawRequest("from-login", 10);
        when(repository.findByLogin("from-login")).thenReturn(Optional.empty());

        assertThrows(LoginNotFoundException.class, () ->  service.withdraw(request));

        verify(repository).findByLogin("from-login");
        verify(repository, never()).save(fromUpdAccount);
    }

    @Test
    void withdraw_InsufficientFundsException() {
        WithdrawRequest request = new WithdrawRequest("from-login", 999999);
        when(repository.findByLogin("from-login")).thenReturn(Optional.of(fromAccount));

        assertThrows(InsufficientFundsException.class, () ->  service.withdraw(request));

        verify(repository).findByLogin("from-login");
        verify(repository, never()).save(fromUpdAccount);
    }

    @Test
    void transfer_ok() {
        TransferRequest request = new TransferRequest("from-login", "to-login", 10);
        when(repository.findByLogin("from-login")).thenReturn(Optional.of(fromAccount));
        when(repository.findByLogin("to-login")).thenReturn(Optional.of(toAccount));

        BalanceResponse response = service.transfer(request);

        assertNotNull(response);
        assertFalse(Strings.isBlank(response.info()));
        verify(repository).findByLogin("from-login");
        verify(repository).save(fromUpdAccount);
        verify(repository).findByLogin("to-login");
        verify(repository).save(toUpdAccount);
    }

    @Test
    void transfer_InsufficientFundsException() {
        TransferRequest request = new TransferRequest("from-login", "to-login", 999999);
        when(repository.findByLogin("from-login")).thenReturn(Optional.of(fromAccount));

        assertThrows(InsufficientFundsException.class, () ->  service.transfer(request));

        verify(repository).findByLogin("from-login");
        verify(repository, never()).save(fromUpdAccount);
        verify(repository, never()).findByLogin("to-login");
        verify(repository, never()).save(toUpdAccount);
    }

}