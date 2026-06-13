package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

public record TransferRequest(
        @RequestParam("value")
        @NotNull
        @Min(value = 0, message = "Value must be greater than 0")
        int value,

        @RequestParam("recipient")
        @NotBlank(message = "Login is required")
        String recipient
) {
}
