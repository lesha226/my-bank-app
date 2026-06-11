package ru.yandex.practicum.mybank.service.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.account.dto.AccountDetailResponse;
import ru.yandex.practicum.mybank.service.account.dto.UpdateAccountRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{login}/detail")
    @PreAuthorize("hasRole('USER') && hasAuthority('account.write')")
    public ResponseEntity<AccountDetailResponse> getAccountDetail(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login
    ) {
        System.out.println("AccountController.getAccount login=" + login + ", jwt=" + getJwtInfo(jwt));

        AccountDetailResponse response = accountService.getAccountDetail(login);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{login}")
    @PreAuthorize("hasRole('USER') && hasAuthority('account.write')")
    public ResponseEntity<ServiceResponse> updateAccount(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("login") String login,
            @RequestBody UpdateAccountRequest request
    ) {
        System.out.println("AccountController.updateAccount login=" + login + ", request=" + request + ", jwt=" + getJwtInfo(jwt));

        ServiceResponse response = accountService.updateAccount(login, request);

        System.out.println("AccountController.getAccount response=" + response);

        return ResponseEntity.ok(response);
    }

    private static String getJwtInfo(Jwt jwt) {
        if (jwt == null) {
            return "null";
        }
        StringBuilder info = new StringBuilder()
                .append("[")
                //.append("getHeaders=" + jwt.getHeaders() + ", ")
                //.append("getAudience=" + jwt.getAudience() + ", ")
                //.append("getClaims=" + jwt.getClaims() + ", ")
                //.append("getId=" + jwt.getId() + ", ")
                //.append("getSubject=" + jwt.getSubject() + ", ")
                //.append("getTokenValue=" + jwt.getTokenValue() + ", ")
                .append("login=" + jwt.getClaimAsString("preferred_username") + ", ")
                .append("realm_access=" + jwt.getClaimAsMap("realm_access") + ", ")
                .append("]");

        //info.append("jwt=" + jwt);
        return info.toString();
    }


}
