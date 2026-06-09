package ru.yandex.practicum.mybank.service.transfer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
        @NotBlank(message = "Login is required")
        String toLogin,

        @NotNull
        @Min(value = 0, message = "Value must be greater than 0")
        int amount
) {
}
