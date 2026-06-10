package ru.yandex.practicum.mybank.service.cash.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.cash.dto.AccountsResponse;
import ru.yandex.practicum.mybank.service.cash.model.CashActionRequest;
import ru.yandex.practicum.mybank.service.cash.service.CashService;

@RestController
@RequestMapping("/api/v1/cash")
public class CashController {

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("/{login}/action")
    @PreAuthorize("hasRole('USER') && hasAuthority('cash.write')")
    public ResponseEntity<AccountsResponse> action(
            @PathVariable("login") String login,
            @Valid @RequestBody CashActionRequest params,
            @AuthenticationPrincipal Jwt jwt
    ) {
        System.out.println("CashController.action login=" + login + ", params=" + params  + ", jwt=" + jwt);

        AccountsResponse response = cashService.action(login, params);

        System.out.println("CashController.action response=" + response);

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
        //info.append("getSubject=" + jwt.getSubject() + ", ");
        //info.append("getTokenValue=" + jwt.getTokenValue() + ", ");
        info.append("preferred_username=" + jwt.getClaimAsString("preferred_username") + ", ");
        info.append("realm_access=" + jwt.getClaimAsMap("realm_access") + ", ");

        //info.append("jwt=" + jwt);
        return info.toString();
    }
}
