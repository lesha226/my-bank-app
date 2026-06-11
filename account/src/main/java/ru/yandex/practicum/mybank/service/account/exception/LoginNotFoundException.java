package ru.yandex.practicum.mybank.service.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginNotFoundException extends ResponseStatusException {


    public LoginNotFoundException(String login) {
        super(HttpStatus.NOT_FOUND, String.format("Login '%s' not found", login));
    }
}
