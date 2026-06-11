package ru.yandex.practicum.mybank.service.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.mybank.service.account.dto.AccountLiteResponse;
import ru.yandex.practicum.mybank.service.account.dto.AccountDetailResponse;
import ru.yandex.practicum.mybank.service.account.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.model.Account;
import ru.yandex.practicum.mybank.service.account.outbox.OutboxService;
import ru.yandex.practicum.mybank.service.account.repository.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OutboxService outboxService;

    private final static String login = "user";
    private AccountDetailResponse accountDetailResponse;
    private Account account;
    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        account = new Account(1L, "user", "fullname", LocalDate.ofYearDay(2001, 1), 100);
        accounts = List.of(
                new Account(2L, "user2", "fullname2", LocalDate.ofYearDay(2001, 2), 200),
                new Account(3L, "user3", "fullname3", LocalDate.ofYearDay(2001, 3), 300)
        );
        accountDetailResponse = new AccountDetailResponse(
                "user",
                "fullname",
                LocalDate.ofYearDay(2001, 1),
                100,
                List.of(
                        new AccountLiteResponse("user2", "fullname2"),
                        new AccountLiteResponse("user3", "fullname3")
                )
        );
    }

    @Test
    void getAccountDetail() {

        when(accountRepository.findByLogin(login)).thenReturn(Optional.of(account));
        when(accountRepository.findAllByLoginNot(login)).thenReturn(accounts);

        AccountDetailResponse result = accountService.getAccountDetail(login);

        assertEquals(result, accountDetailResponse);

        verify(accountRepository).findByLogin(login);
        verify(accountRepository).findAllByLoginNot(login);
    }

    @Test
    void updateAccount() throws JsonProcessingException {
        UpdateAccountRequest request = new UpdateAccountRequest("FullNameUpd", LocalDate.ofYearDay(2001, 10));
        Account updAccount = new Account(1L, "user", "FullNameUpd",
                LocalDate.ofYearDay(2001, 10), 100);

        when(accountRepository.findByLogin(login)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(updAccount);

        when(objectMapper.writeValueAsString(any())).thenReturn("<body>");

        ServiceResponse result = accountService.updateAccount(login, request);

        assertNotNull(result);
        assertFalse(Strings.isBlank(result.info()));

        verify(accountRepository).findByLogin(login);
        verify(accountRepository).save(any());
        verify(objectMapper).writeValueAsString(any());
        verify(outboxService).asyncNotify(any());
    }
}