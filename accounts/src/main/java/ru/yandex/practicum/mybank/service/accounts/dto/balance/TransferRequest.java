package ru.yandex.practicum.mybank.service.accounts.dto.balance;

public record TransferRequest(
        String fromLogin,
        String toLogin,
        int amount
) {
}
