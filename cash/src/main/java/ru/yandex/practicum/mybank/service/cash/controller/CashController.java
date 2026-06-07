package ru.yandex.practicum.mybank.service.cash.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.cash.dto.CashActionRequest;
import ru.yandex.practicum.mybank.service.cash.service.CashService;

@RestController
@RequestMapping("/api/v1/cash")
public class CashController {

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("/{login}/action")
    public ResponseEntity<Void> action(
            @PathVariable("login") String login,
            @Valid @RequestBody CashActionRequest params,
            @AuthenticationPrincipal Jwt jwt
    ) {
        System.out.println("CashController.action login=" + login + ", params=" + params  + ", jwt=" + jwt);

        cashService.action(login, params, jwt);

        System.out.println("CashController.action done");

        return ResponseEntity.noContent().build();
    }
}
