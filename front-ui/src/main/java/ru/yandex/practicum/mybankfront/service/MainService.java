package ru.yandex.practicum.mybankfront.service;

import jakarta.annotation.Nonnull;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybankfront.client.AccountClient;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.practicum.mybankfront.client.dto.ServiceResponse;
import ru.yandex.practicum.mybankfront.controller.dto.*;
import ru.yandex.practicum.mybankfront.client.dto.AccountDetailResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    private final AccountClient accountsClient;
    private final CashClient cashClient;
    private final TransferClient transferClient;

    public MainService(AccountClient accountClient, CashClient cashClient, TransferClient transferClient) {
        this.accountsClient = accountClient;
        this.cashClient = cashClient;
        this.transferClient = transferClient;
    }

    private final static List<String> noErrors = List.of();

    public @Nonnull AccountResponse getAccountDetail(OidcUser user, ExecutionStatusResponse lastResult) {
        if (user == null || lastResult == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("MainService.getAccount user=" + user.getName());

        try {
            AccountDetailResponse account = accountsClient.getAccountDetail(user.getName());
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
            ServiceResponse response = accountsClient.updateAccount(user.getName(), params);

            return new ExecutionStatusResponse(noErrors, response.info());
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
            ServiceResponse response = cashClient.action(user.getName(), params);

            return new ExecutionStatusResponse(noErrors, response.info());
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
            ServiceResponse response = transferClient.transfer(user.getName(), params);

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

    private AccountResponse toAccountResponse(@Nonnull AccountDetailResponse account, @Nonnull ExecutionStatusResponse lastResult) {

        String birthdate = null;
        if (account.birthdate() != null) {
            birthdate = account.birthdate().format(DateTimeFormatter.ISO_DATE);
        }

        return new AccountResponse(account.name(), birthdate, account.balanceAmount(), account.contacts()
                , lastResult.errors(), lastResult.info());
    }
}
