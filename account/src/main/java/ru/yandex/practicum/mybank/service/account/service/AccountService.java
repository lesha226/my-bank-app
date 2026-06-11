package ru.yandex.practicum.mybank.service.account.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mybank.service.account.dto.AccountDetailResponse;
import ru.yandex.practicum.mybank.service.account.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.account.model.Account;
import ru.yandex.practicum.mybank.service.account.outbox.Outbox;
import ru.yandex.practicum.mybank.service.account.outbox.OutboxService;
import ru.yandex.practicum.mybank.service.account.repository.AccountRepository;
import ru.yandex.practicum.mybank.service.account.service.mapper.AccountMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper = AccountMapper.ACCOUNT_MAPPER;
    private final OutboxService outboxService;
    private final ObjectMapper objectMapper;

    public AccountService(AccountRepository accountRepository, OutboxService outboxService, ObjectMapper objectMapper) {
        this.accountRepository = accountRepository;
        this.outboxService = outboxService;
        this.objectMapper = objectMapper;
    }

    public AccountDetailResponse getAccountDetail(String login) {
        System.out.println("AccountService.getAccount: login=" + login);
        if (login == null || Strings.isBlank(login)) {
            throw new IllegalArgumentException();
        }

        Account account = accountRepository.findByLogin(login)
                .orElseThrow(() -> new LoginNotFoundException(login));
        List<Account> contacts = accountRepository.findAllByLoginNot(login);
        System.out.println("account=" + account + ", accounts" + contacts);
        AccountDetailResponse dto = mapper.toDto(account, contacts);

        System.out.println("AccountService.getAccount: result=" + dto);
        return dto;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ServiceResponse updateAccount(String login, UpdateAccountRequest request) {
        System.out.println("AccountService.updateAccount: login=" + login);
        if (login == null || Strings.isBlank(login) || request == null) {
            throw new IllegalArgumentException();
        }

        Account account = accountRepository.findByLogin(login)
                .orElseThrow(() -> new LoginNotFoundException(login));

        //mapper.updateFromDto(account, request);
        account.setName(request.name());
        account.setBirthdate(request.birthdate());
        account = accountRepository.save(account);

        String info = "Аккаунт изменен";
        ServiceResponse response = new ServiceResponse(info);

        asyncNotify(login, request, response);

        System.out.println("AccountService.updateAccount: result=" + response);
        return response;
    }

    private void asyncNotify(String login, UpdateAccountRequest request, ServiceResponse response) {
        System.out.println("AccountService.asyncNotify: login=" + login + ", request=" + request + ", response=" + response);
        try {
            UpdateAccountOutboxBody updateAccountOutboxBody = new UpdateAccountOutboxBody(request, response);
            //String body = getAccountDetailOutboxBody.toString();
            String body = objectMapper.writeValueAsString(updateAccountOutboxBody);  // TODO : switch to objectMapper
            Outbox outbox = new Outbox(null, "account.updateAccount.v1", login, LocalDateTime.now(), body);

            outboxService.asyncNotify(outbox);
        } catch (Exception e) {
            System.out.println("ERROR AccountService.asyncNotify: " + e.getMessage());
        }
    }

    private static record UpdateAccountOutboxBody(UpdateAccountRequest request, ServiceResponse response) {};

}
