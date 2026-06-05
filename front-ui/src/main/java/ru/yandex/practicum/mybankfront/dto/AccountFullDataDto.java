package ru.yandex.practicum.mybankfront.dto;

import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;

import java.time.LocalDate;
import java.util.List;

public record AccountFullDataDto(
        String login,
        String name,
        LocalDate birthdate,
        int balanceAmount,
        List<AccountDto> contacts
) {
}
