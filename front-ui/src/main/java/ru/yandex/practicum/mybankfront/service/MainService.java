package ru.yandex.practicum.mybankfront.service;

import jakarta.annotation.Nonnull;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybankfront.client.AccountsClient;
import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;
import ru.yandex.practicum.mybankfront.controller.dto.MainResponse;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;
import ru.yandex.practicum.mybankfront.dto.AccountFullDataDto;

import java.time.LocalDate;
import java.util.List;

@Service
public class MainService {

    private final AccountsClient accountsClient;

    public MainService(AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    public @Nonnull MainResponse getAccount(OidcUser user) {
        System.out.println("MainService.getAccount user=" + user);

        if (user == null) {
            throw new IllegalArgumentException();
        }

        try {
            AccountFullDataDto account = accountsClient.getAccount(user.getPreferredUsername());
            return toMainResponse(account, List.of(), null);
        } catch (Exception e) {
            return toMainResponse(List.of(e.getMessage()));
        }
    }

    public @Nonnull MainResponse editAccount(OidcUser user, EditAccountRequest params) {
        System.out.println("MainService.editAccount user=" + user + ", params=" + params);

        if (user == null || params == null ) {
            throw new IllegalArgumentException();
        }

        try {
            AccountFullDataDto account = accountsClient.updateAccount(user.getPreferredUsername(), params);
            return toMainResponse(account, List.of(), "Saved");
        } catch (Exception e) {
            return toMainResponse(List.of(e.getMessage()));
        }
    }

    public @Nonnull MainResponse editCash(OidcUser user, EditCashRequest params) {
        System.out.println("MainService.editCash user=" + user + ", params=" + params);

        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }

        List<String> errors = List.of();
        String info = null;

        // TODO : выполнить действие

        try {
            AccountFullDataDto account = accountsClient.getAccount(user.getPreferredUsername());
            return toMainResponse(account, errors, info);
        } catch (Exception e) {
            return toMainResponse(List.of(e.getMessage()));
        }
    }

    public @Nonnull MainResponse transfer(OidcUser user, TransferRequest params) {
        System.out.println("MainService.transfer user=" + user + ", params=" + params);

        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }

        List<String> errors = List.of();
        String info = null;

        // TODO : выполнить действие

        try {
            AccountFullDataDto account = accountsClient.getAccount(user.getPreferredUsername());
            return toMainResponse(account, errors, info);
        } catch (Exception e) {
            return toMainResponse(List.of(e.getMessage()));
        }
    }

    private MainResponse toMainResponse(List<String> errors) {
        return new MainResponse(null, null, null, List.of(), errors, null);
    }

    private MainResponse toMainResponse(AccountFullDataDto account, List<String> errors, String info) {
        return new MainResponse(account.name(), account.birthdate(), account.balanceAmount(), account.contacts()
                , errors, info);
    }
}
