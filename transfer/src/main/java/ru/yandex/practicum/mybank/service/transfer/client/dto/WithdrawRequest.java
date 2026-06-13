package ru.yandex.practicum.mybank.service.transfer.client.dto;

public record WithdrawRequest(String login, int amount) {}
