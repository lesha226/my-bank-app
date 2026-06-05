package ru.yandex.practicum.mybank.service.accounts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record AccountFullDataDto(
        String login,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        int balanceAmount,
        List<AccountDto> contacts
) {
}
