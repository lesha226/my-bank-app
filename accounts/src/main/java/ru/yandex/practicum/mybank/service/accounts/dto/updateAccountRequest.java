package ru.yandex.practicum.mybank.service.accounts.dto;

import java.time.LocalDate;

public record updateAccountRequest(
        String name,
        LocalDate birthdate
) {}
