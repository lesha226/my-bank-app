package ru.yandex.practicum.mybank.service.accounts.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.model.Account;
import ru.yandex.practicum.mybank.service.accounts.outbox.Outbox;
import ru.yandex.practicum.mybank.service.accounts.outbox.OutboxService;
import ru.yandex.practicum.mybank.service.accounts.repository.AccountsRepository;
import ru.yandex.practicum.mybank.service.accounts.service.mapper.AccountMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final AccountMapper mapper = AccountMapper.ACCOUNT_MAPPER;
    private final OutboxService outboxService;

    public AccountsService(AccountsRepository accountsRepository, OutboxService outboxService) {
        this.accountsRepository = accountsRepository;
        this.outboxService = outboxService;
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

        asyncNotify(login, request, dto);

        System.out.println("AccountsService.updateAccount: result=" + dto);
        return dto;
    }

    private void asyncNotify(String login, UpdateAccountRequest request, AccountFullDataDto response) {
        System.out.println("AccountsService.asyncNotify: login=" + login + ", request=" + request + ", response=" + response);
        try {
            AccountOutboxBody accountOutboxBody = new AccountOutboxBody(request, response);
            String body = accountOutboxBody.toString();//objectMapper.writeValueAsString(cashActionBody);  // TODO : switch to objectMapper
            Outbox outbox = new Outbox(null, "accounts.updateAccount.v1", login, LocalDateTime.now(), body);

            outboxService.asyncNotify(outbox);
        } catch (Exception e) {
            System.out.println("ERROR AccountsService.asyncNotify: " + e.getMessage());
        }
    }

    private static record AccountOutboxBody(UpdateAccountRequest request, AccountFullDataDto response) {};

}
