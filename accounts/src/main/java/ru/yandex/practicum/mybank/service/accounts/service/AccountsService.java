package ru.yandex.practicum.mybank.service.accounts.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountDto;
import ru.yandex.practicum.mybank.service.accounts.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.model.Account;
import ru.yandex.practicum.mybank.service.accounts.repository.AccountsRepository;
import ru.yandex.practicum.mybank.service.accounts.service.mapper.AccountMapper;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final AccountMapper mapper = AccountMapper.ACCOUNT_MAPPER;

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

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public AccountFullDataDto getAccount(String login) {
        System.out.println("AccountsService.getAccount: login=" + login);

        Account account = accountsRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Not found"));
        List<Account> accounts = accountsRepository.findAllByLoginNot(login);
        System.out.println("account=" + account + ", accounts" + accounts);
        AccountFullDataDto dto = mapper.toDto(account, accounts);

        System.out.println("AccountsService.getAccount: result=" + dto);
        return dto;
    }

    public AccountFullDataDto updateAccount(String login, UpdateAccountRequest request) {
        System.out.println("AccountsService.updateAccount: login=" + login);

        Account account = accountsRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Not found"));

        mapper.updateFromDto(account, request);
        account = accountsRepository.save(account);

        List<Account> accounts = accountsRepository.findAllByLoginNot(login);
        AccountFullDataDto dto = mapper.toDto(account, accounts);

        System.out.println("AccountsService.updateAccount: result=" + dto);
        return dto;
    }

}
