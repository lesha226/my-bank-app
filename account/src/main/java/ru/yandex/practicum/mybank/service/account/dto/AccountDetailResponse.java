package ru.yandex.practicum.mybank.service.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record AccountDetailResponse(
        String login,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        int balanceAmount,
        List<AccountLiteResponse> contacts
) {
}
