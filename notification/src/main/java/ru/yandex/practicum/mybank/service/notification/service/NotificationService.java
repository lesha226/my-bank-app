package ru.yandex.practicum.mybank.service.notification.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.notification.model.NotifyRequest;

@Service
public class NotificationService {
    public void notify(NotifyRequest request) {
        System.out.println("<=== MESSAGE ===>: " + request);
    }
}
