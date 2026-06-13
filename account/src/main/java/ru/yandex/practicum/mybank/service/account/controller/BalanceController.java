package ru.yandex.practicum.mybank.service.account.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.mybank.service.account.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.account.service.BalanceService;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<ServiceResponse> transfer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody TransferRequest request
    ) {
        System.out.println("BalanceController.deposit request=" + request + ", jwt=" + getJwtInfo(jwt));

        ServiceResponse response = balanceService.transfer(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('account.write')")
    public ResponseEntity<ServiceResponse> deposit(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody DepositRequest request
    ) {
        System.out.println("BalanceController.deposit request=" + request + ", jwt=" + getJwtInfo(jwt));

        ServiceResponse response = balanceService.deposit(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('account.write')")
    public ResponseEntity<ServiceResponse> withdraw(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody WithdrawRequest request
    ) {
        System.out.println("BalanceController.withdraw request=" + request + ", jwt=" + getJwtInfo(jwt));

        ServiceResponse response = balanceService.withdraw(request);

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
