package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record AccountResponse(
        @Nullable
        String name,

        @Nullable
        String birthdate,

        @Nullable
        Integer sum,

        @Nonnull
        List<AccountDto> accounts,

        @Nullable
        List<String> errors,

        @Nullable
        String info
) {}
