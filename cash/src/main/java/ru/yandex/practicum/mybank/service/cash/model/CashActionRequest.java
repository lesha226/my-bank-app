package ru.yandex.practicum.mybank.service.cash.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

public record CashActionRequest(
        @RequestParam("value")
        @NotNull
        @Min(value = 1, message = "Value must be greater than 0")
        int value,

        @RequestParam("action")
        @NotNull
        CashAction action) {
}
