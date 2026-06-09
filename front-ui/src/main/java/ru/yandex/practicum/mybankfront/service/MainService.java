package ru.yandex.practicum.mybankfront.service;

import jakarta.annotation.Nonnull;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybankfront.client.AccountsClient;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.practicum.mybankfront.client.dto.TransferClientResponse;
import ru.yandex.practicum.mybankfront.controller.dto.*;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    private final AccountsClient accountsClient;
    private final CashClient cashClient;
    private final TransferClient transferClient;

    public MainService(AccountsClient accountsClient, CashClient cashClient, TransferClient transferClient) {
        this.accountsClient = accountsClient;
        this.cashClient = cashClient;
        this.transferClient = transferClient;
    }

    private final static List<String> noErrors = List.of();

    public @Nonnull AccountResponse getAccount(OidcUser user, ExecutionStatusResponse lastResult) {
        if (user == null || lastResult == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("MainService.getAccount user=" + user.getName());

        try {
            AccountFullDataDto account = accountsClient.getAccount(user.getName());
            return toAccountResponse(account, lastResult);
        } catch (Exception e) {
            return toAccountResponse(e.getMessage(), lastResult);
        }
    }

    public @Nonnull ExecutionStatusResponse editAccount(OidcUser user, EditAccountRequest params) {
        if (user == null || params == null ) {
            throw new IllegalArgumentException();
        }
        System.out.println("MainService.editAccount user=" + user.getName() + ", params=" + params);

        try {
            AccountFullDataDto account = accountsClient.updateAccount(user.getName(), params);
            return doCompleteResult();
        } catch (Exception e) {
            return doErrorResult(e.getMessage());
        }
    }

    public @Nonnull ExecutionStatusResponse editCash(OidcUser user, EditCashRequest params) {
        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("MainService.editCash user=" + user.getName() + ", params=" + params);

        try {
            cashClient.action(user.getName(), params);

            return doCompleteResult();
        } catch (Exception e) {
            return doErrorResult(e.getMessage());
        }
    }

    public @Nonnull ExecutionStatusResponse transfer(OidcUser user, TransferRequest params) {
        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("MainService.transfer user=" + user.getName() + ", params=" + params);

        try {
            TransferClientResponse response = transferClient.transfer(user.getName(), params);

            return new ExecutionStatusResponse(noErrors, response.info());
        } catch (Exception e) {
            return doErrorResult(e.getMessage());
        }
    }

    private ExecutionStatusResponse doErrorResult(String message) {
        return new ExecutionStatusResponse(List.of(message), null);
    }

    private ExecutionStatusResponse doCompleteResult() {
        return new ExecutionStatusResponse(noErrors, "Done!");
    }

    private AccountResponse toAccountResponse(@Nonnull String errorMessage, @Nonnull ExecutionStatusResponse lastResult) {
        List<String> errors = new ArrayList<>(lastResult.errors());
        errors.add(errorMessage);

        return new AccountResponse(null, null, null, List.of(), errors, lastResult.info());
    }

    private AccountResponse toAccountResponse(@Nonnull AccountFullDataDto account, @Nonnull ExecutionStatusResponse lastResult) {

        String birthdate = null;
        if (account.birthdate() != null) {
            birthdate = account.birthdate().format(DateTimeFormatter.ISO_DATE);
        }

        return new AccountResponse(account.name(), birthdate, account.balanceAmount(), account.contacts()
                , lastResult.errors(), lastResult.info());
    }
}
