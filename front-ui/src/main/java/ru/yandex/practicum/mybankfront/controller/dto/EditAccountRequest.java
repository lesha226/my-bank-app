package ru.yandex.practicum.mybankfront.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.Period;

public record EditAccountRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Birthdate is required")
        LocalDate birthdate
) {

    public EditAccountRequest(
            @NotBlank(message = "Name is required") String name,
            @NotNull(message = "Birthdate is required") LocalDate birthdate
    ) {
        if (Period.between(birthdate, LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("Birthdate must be at least 18 years old");
        }

        this.name = name;
        this.birthdate = birthdate;
    }
}
