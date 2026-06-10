package ru.yandex.practicum.mybank.service.cash.dto;

public record AccountsWithdrawRequest(String fromLogin, int amount) {
}
