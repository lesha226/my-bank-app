package ru.yandex.practicum.mybank.service.accounts.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountDto;
import ru.yandex.practicum.mybank.service.accounts.dto.updateAccountRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountsService {

    // TODO : удалить заглушку
    private final static AccountFullDataDto ACCOUNT_RESPONSE = new AccountFullDataDto(
            "test-user",
            "Test user",
            LocalDate.of(2001, 1, 1),
            123,
            List.of(
                    new AccountDto("test-user1", "Test user1"),
                    new AccountDto("test-user2", "Test user2")
            )
    );

    public AccountFullDataDto getAccount(String login) {
        return ACCOUNT_RESPONSE;
    }

    public AccountFullDataDto updateAccount(String login, updateAccountRequest request) {
        return ACCOUNT_RESPONSE;
    }
}
