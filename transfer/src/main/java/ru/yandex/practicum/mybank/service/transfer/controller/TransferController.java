package ru.yandex.practicum.mybank.service.transfer.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.transfer.model.TransferResponse;
import ru.yandex.practicum.mybank.service.transfer.model.TransferRequest;
import ru.yandex.practicum.mybank.service.transfer.service.TransferService;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<String> test(@PathVariable("login") String login) {
        return ResponseEntity.ok("login=" + login);
    }

    @PostMapping("/{login}")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<TransferResponse> transfer(
            @PathVariable("login") String login,
            @Valid @RequestBody TransferRequest params,
            @AuthenticationPrincipal Jwt jwt
    ) {
        System.out.println("TransferController.transfer login=" + login + ", params=" + params  + ", jwt=" + getJwtInfo(jwt));

        TransferResponse response = transferService.transfer(login, params);

        System.out.println("TransferController.transfer response=" + response);

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
