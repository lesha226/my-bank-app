package ru.yandex.practicum.mybank.service.cash.service;

import jakarta.validation.Valid;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.cash.dto.CashActionRequest;

@Service
public class CashService {

    public void action(String login, @Valid CashActionRequest params, Jwt jwt) {

    }

}
