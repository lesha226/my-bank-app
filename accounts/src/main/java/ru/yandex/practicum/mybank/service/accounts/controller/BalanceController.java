package ru.yandex.practicum.mybank.service.accounts.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.BalanceResponse;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.accounts.service.BalanceService;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<BalanceResponse> transfer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody TransferRequest request
    ) {
        System.out.println("BalanceController.deposit request=" + request + ", jwt=" + getJwtInfo(jwt));

        BalanceResponse response = balanceService.transfer(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<BalanceResponse> deposit(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody DepositRequest request
    ) {
        System.out.println("BalanceController.deposit request=" + request + ", jwt=" + getJwtInfo(jwt));

        BalanceResponse response = balanceService.deposit(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<BalanceResponse> withdraw(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody WithdrawRequest request
    ) {
        System.out.println("BalanceController.withdraw request=" + request + ", jwt=" + getJwtInfo(jwt));

        BalanceResponse response = balanceService.withdraw(request);

        return ResponseEntity.ok(response);
    }

    private String getJwtInfo(Jwt jwt) {
        if (jwt == null) {
            return "";
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
