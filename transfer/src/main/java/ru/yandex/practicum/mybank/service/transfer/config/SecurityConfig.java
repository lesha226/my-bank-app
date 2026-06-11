package ru.yandex.practicum.mybank.service.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        //.requestMatchers("/**").permitAll() // TODO : delete !!!
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().write(
                                    accessDeniedException.getMessage() != null
                                            ? accessDeniedException.getMessage()
                                            : "Доступ запрещён"
                            );
                        })
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractRealmRoles);
        return converter;
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return Collections.emptyList();
        }

        Object rolesObj = realmAccess.get("roles");

        // Если провайдер вдруг вернёт не коллекцию (например, строку или null),
        // мы не упадём с ClassCastException,
        // а просто считаем, что ролей нет.
        if (!(rolesObj instanceof Collection<?> rawRoles)) {
            return Collections.emptyList();
        }

        // Приводим всё к списку строк
        List<String> roles = rawRoles.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .toList();

        // Добавляем "ROLE_<имя роли>" для @PreAuthorize("hasRole('...')")
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        if (roles.contains("TRANSFER_WRITE")) {
            authorities.add(new SimpleGrantedAuthority("transfer.write"));
        }

        return authorities;
    }

}
