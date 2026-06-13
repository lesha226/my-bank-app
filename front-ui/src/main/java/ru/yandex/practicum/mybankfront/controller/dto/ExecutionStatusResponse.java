package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.annotation.Nonnull;

import java.util.List;

public record ExecutionStatusResponse(@Nonnull List<String> errors, String info) {
}
