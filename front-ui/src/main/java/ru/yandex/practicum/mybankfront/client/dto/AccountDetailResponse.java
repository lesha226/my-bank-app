package ru.yandex.practicum.mybankfront.client.dto;

import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;

import java.time.LocalDate;
import java.util.List;

public record AccountDetailResponse(
        String login,
        String name,
        LocalDate birthdate,
        int balanceAmount,
        List<AccountDto> contacts
) {
}
