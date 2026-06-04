package ru.yandex.practicum.mybankfront.service;

import jakarta.annotation.Nonnull;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;
import ru.yandex.practicum.mybankfront.controller.dto.MainResponse;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class MainService {

    // TODO : удалить заглушку
    private final static MainResponse MAIN_RESPONSE_STUB = new MainResponse(
            "Иванов Иван", LocalDate.of(2001, 1, 1), 100,
            List.of(
                    new AccountDto("petrov", "Петров Петр"),
                    new AccountDto("sidorov", "Сидоров Сидор")
            ),
            List.of("error"), "info");

    public @Nonnull MainResponse getAccount(OidcUser user) {
        System.out.println("MainService.getAccount user=" + user);

        if (user == null) {
            throw new IllegalArgumentException();
        }

        return MAIN_RESPONSE_STUB;
    }

    public @Nonnull MainResponse editAccount(OidcUser user, EditAccountRequest params) {
        System.out.println("MainService.editAccount user=" + user + ", params=" + params);

        if (user == null || params == null ) {
            throw new IllegalArgumentException();
        }

        return MAIN_RESPONSE_STUB;
    }

    public @Nonnull MainResponse editCash(OidcUser user, EditCashRequest params) {
        System.out.println("MainService.editCash user=" + user + ", params=" + params);

        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }

        return MAIN_RESPONSE_STUB;
    }

    public @Nonnull MainResponse transfer(OidcUser user, TransferRequest params) {
        System.out.println("MainService.transfer user=" + user + ", params=" + params);

        if (user == null || params == null) {
            throw new IllegalArgumentException();
        }

        return MAIN_RESPONSE_STUB;
    }
}
