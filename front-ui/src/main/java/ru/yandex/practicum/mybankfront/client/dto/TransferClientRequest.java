package ru.yandex.practicum.mybankfront.client.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferClientRequest(
        @NotBlank(message = "Login is required")
        String toLogin,

        @NotNull
        @Min(value = 0, message = "Value must be greater than 0")
        int amount) {
}
