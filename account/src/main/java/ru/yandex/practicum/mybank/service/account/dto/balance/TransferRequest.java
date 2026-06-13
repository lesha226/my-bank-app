package ru.yandex.practicum.mybank.service.account.dto.balance;

public record TransferRequest(
        String fromLogin,
        String toLogin,
        int amount
) {
}
