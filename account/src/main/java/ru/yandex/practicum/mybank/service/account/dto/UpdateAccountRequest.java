package ru.yandex.practicum.mybank.service.account.dto;

import java.time.LocalDate;

public record UpdateAccountRequest(
        String name,
        LocalDate birthdate
) {}
