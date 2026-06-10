package ru.yandex.practicum.mybank.service.cash.outbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotificationClient {

    private final String baseUrl;
    private final WebClient webClient;

    public NotificationClient(@Value("${bank.service.notification.base-url}") String baseUrl, WebClient.Builder serviceClientBuilder) {
        this.baseUrl = baseUrl;
        this.webClient = serviceClientBuilder.baseUrl(baseUrl).build();
    }

    public void notify(NotificationRequest request) {
        System.out.println("NotificationClient.notify: request=" + request + ", baseUrl=" + baseUrl);

        webClient.post()
                .uri("/notification")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        System.out.println("NotificationClient.notify: done");
    }


}
