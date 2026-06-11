package ru.yandex.practicum.mybank.service.account.dto.balance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepositRequest(
        @NotBlank(message = "Login is required")
        String toLogin,

        @NotNull
        @Min(value = 0, message = "Amount must be greater than 0")
        int amount
) {
}