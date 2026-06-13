package ru.yandex.practicum.mybank.service.cash.outbox;

import java.time.LocalDateTime;

public record NotificationRequest(String src, String login, LocalDateTime createdAt, String body) {
}
