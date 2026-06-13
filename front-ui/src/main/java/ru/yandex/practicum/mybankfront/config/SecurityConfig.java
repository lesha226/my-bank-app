package ru.yandex.practicum.mybankfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${bank.logout-url:http://localhost:8180/realms/bank-realm/protocol/openid-connect/logout}")
    String logout_url;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        //.logoutSuccessUrl("/").permitAll()
                        .logoutSuccessUrl(logout_url).permitAll()
                        //.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)) // Твой хендлер
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {

        // Для работы необходимо указать ClientRegistrationRepository
        // Этот репозиторий содержит информацию обо всех зарегистрированных в приложении клиентах (и их провайдерах).
        OidcClientInitiatedLogoutSuccessHandler handler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        // Можно указать адрес после успешного логаута
        handler.setPostLogoutRedirectUri("{baseUrl}");
        /*String logoutUrl = UriComponentsBuilder.fromUriString("http://localhost:8180/realms/bank-realm/protocol/openid-connect/logout")
                .queryParam("redirect_uri", "http://localhost:8080/oauth2/authorization/keycloak")
                .build()
                .toUriString();
        handler.setPostLogoutRedirectUri(logoutUrl);*/

        return handler;
    }
}
