package ru.yandex.practicum.mybank.service.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.updateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.service.AccountsService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<AccountFullDataDto> getAccount(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login
    ) {
        System.out.println("AccountController.getAccount login=" + login + ", jwt=" + jwt);

        AccountFullDataDto response = accountsService.getAccount(login);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{login}")
    public ResponseEntity<AccountFullDataDto> updateAccount(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login,
            @RequestBody updateAccountRequest request
    ) {
        System.out.println("AccountController.updateAccount login=" + login + ", request=" + request + ", jwt=" + jwt);

        AccountFullDataDto response = accountsService.updateAccount(login, request);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }
}
