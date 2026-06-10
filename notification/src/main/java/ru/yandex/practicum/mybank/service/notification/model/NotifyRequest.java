package ru.yandex.practicum.mybank.service.notification.model;

import java.time.LocalDateTime;

public record NotifyRequest(String src, String login, LocalDateTime createdAt, String body) {
}
