package ru.yandex.practicum.mybank.service.accounts.dto.balance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WithdrawRequest(
        @NotBlank(message = "Login is required")
        String fromLogin,

        @NotNull
        @Min(value = 0, message = "Amount must be greater than 0")
        int amount
) {
}
