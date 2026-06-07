package ru.yandex.practicum.mybank.service.transfer.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.transfer.dto.TransferRequest;

@Service
public class TransferService {

    public void transfer(String login, TransferRequest params, Jwt jwt) {
        System.out.println("TransferService.transfer login=" + login + ", params=" + params  + ", jwt=" + jwt);
    }

}
