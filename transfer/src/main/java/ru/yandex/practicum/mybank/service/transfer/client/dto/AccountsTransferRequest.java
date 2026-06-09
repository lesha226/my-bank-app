package ru.yandex.practicum.mybank.service.transfer.client.dto;

public record AccountsTransferRequest(
        String fromLogin,
        String toLogin,
        int amount) {
}
