package ru.yandex.practicum.mybank.service.transfer.client.dto;

public record DepositRequest(String login, int amount) {}