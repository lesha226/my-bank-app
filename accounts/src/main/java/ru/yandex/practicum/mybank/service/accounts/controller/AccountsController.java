package ru.yandex.practicum.mybank.service.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.accounts.dto.AccountFullDataDto;
import ru.yandex.practicum.mybank.service.accounts.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.accounts.service.AccountsService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/{login}")
    @PreAuthorize("hasRole('USER') && authentication.name == #login")
    public ResponseEntity<AccountFullDataDto> getAccount(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login
    ) {
        System.out.println("AccountController.getAccount login=" + login + ", jwt=" + getJwtInfo(jwt));

        AccountFullDataDto response = accountsService.getAccount(login);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{login}")
    @PreAuthorize("hasRole('USER') && authentication.name == #login")
    public ResponseEntity<AccountFullDataDto> updateAccount(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login,
            @RequestBody UpdateAccountRequest request
    ) {
        System.out.println("AccountController.updateAccount login=" + login + ", request=" + request + ", jwt=" + getJwtInfo(jwt));

        AccountFullDataDto response = accountsService.updateAccount(login, request);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }

    private String getJwtInfo(Jwt jwt) {
        if (jwt == null) {
            return "null";
        }
        StringBuilder info = new StringBuilder();
        //info.append("getHeaders=" + jwt.getHeaders() + ", ");
        //info.append("getAudience=" + jwt.getAudience() + ", ");
        //info.append("getClaims=" + jwt.getClaims() + ", ");
        //info.append("getId=" + jwt.getId() + ", ");
        info.append("getSubject=" + jwt.getSubject() + ", ");
        //info.append("getTokenValue=" + jwt.getTokenValue() + ", ");
        info.append("preferred_username=" + jwt.getClaimAsString("preferred_username") + ", ");
        info.append("realm_access=" + jwt.getClaimAsMap("realm_access") + ", ");

        //info.append("jwt=" + jwt);
        return info.toString();
    }


}
