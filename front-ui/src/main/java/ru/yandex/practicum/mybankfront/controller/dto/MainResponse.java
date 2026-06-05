package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record MainResponse(
        @Nullable
        String name,

        @Nullable
        LocalDate birthdate,

        @Nullable
        Integer sum,

        @Nonnull
        List<AccountDto> accounts,

        @Nullable
        List<String> errors,

        @Nullable
        String info
) {}
