package ru.yandex.practicum.mybank.service.transfer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

public record TransferRequest(
        @NotNull
        @Min(value = 0, message = "Value must be greater than 0")
        int value,

        @NotBlank(message = "Login is required")
        String recipient
) {
}
