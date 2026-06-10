package ru.yandex.practicum.mybank.service.notification.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.mybank.service.notification.model.NotifyRequest;
import ru.yandex.practicum.mybank.service.notification.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('notification.write')")
    public void notify(
            @Valid @RequestBody NotifyRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        System.out.println("NotificationController.notify: request=" + request + ", jwt=" + getJwtInfo(jwt));
        service.notify(request);
    }

    private String getJwtInfo(Jwt jwt) {
        if (jwt == null) {
            return "null";
        }
        StringBuilder info = new StringBuilder();
        //info.append("getHeaders=" + jwt.getHeaders() + ", ");
        //info.append("getAudience=" + jwt.getAudience() + ", ");
        //info.append("getClaims=" + jwt.getClaims() + ", ");
        //info.append("getId=" + jwt.getId() + ", ");
        //info.append("getSubject=" + jwt.getSubject() + ", ");
        //info.append("getTokenValue=" + jwt.getTokenValue() + ", ");
        info.append("preferred_username=" + jwt.getClaimAsString("preferred_username") + ", ");
        info.append("realm_access=" + jwt.getClaimAsMap("realm_access") + ", ");

        //info.append("jwt=" + jwt);
        return info.toString();
    }

}
