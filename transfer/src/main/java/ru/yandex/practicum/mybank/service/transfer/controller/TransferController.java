package ru.yandex.practicum.mybank.service.transfer.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mybank.service.transfer.dto.TransferRequest;
import ru.yandex.practicum.mybank.service.transfer.service.TransferService;

import java.util.Map;

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
    public ResponseEntity<Void> transfer(
            @PathVariable("login") String login,
            @Valid @RequestBody TransferRequest params,
            //@RequestBody Map<String, String> params,
            @AuthenticationPrincipal Jwt jwt
    ) {
        System.out.println("TransferController.transfer login=" + login + ", params=" + params  + ", jwt=" + jwt);

        transferService.transfer(login, params, jwt);

        System.out.println("TransferController.transfer done");

        return ResponseEntity.noContent().build();
    }
}
