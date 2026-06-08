package ru.yandex.practicum.mybank.service.accounts.dto;

import java.time.LocalDate;

public record UpdateAccountRequest(
        String name,
        LocalDate birthdate
) {}
