package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record MainResponse(
        @Nonnull
        String name,

        @Nonnull
        LocalDate birthdate,

        int sum,

        @Nonnull
        List<AccountDto> accounts,

        @Nullable
        List<String> errors,

        @Nullable
        String info
) {}
