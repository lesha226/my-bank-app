package ru.yandex.practicum.mybank.service.transfer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("!contract-test")
public class ServiceClientBuilderConfig {

    /**
     * Настраиваем OAuth2AuthorizedClientManager —
     * компонент, который умеет:
     * - создавать сервисный access token по Client Credentials Flow,
     * - обновлять его при истечении,
     * - хранить его в OAuth2AuthorizedClientService.
     * <p>
     * Здесь мы указываем, что transfer-service использует Client Credentials Flow,
     * поэтому включаем только clientCredentials().
     */
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        // Провайдер, который знает, как получать client_credentials токены
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        // Manager, который связывает client registrations и storage токенов
        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientService
                );

        // Говорим менеджеру: для transfer-service используем client_credentials
        manager.setAuthorizedClientProvider(authorizedClientProvider);
        return manager;
    }

    /**
     * WebClient для запросов transfer-service → accounts-service.
     * <p>
     * Важный момент:
     * - этот WebClient автоматически добавляет Authorization: Bearer <service-token>
     * благодаря ServletOAuth2AuthorizedClientExchangeFilterFunction;
     * - вызывающий код не должен думать о токенах вручную.
     */
    /*@Bean
    public WebClient accountsWebClient(
            OAuth2AuthorizedClientManager authorizedClientManager,
            @Value("${bank.service.accounts.base-url}") String accountsServiceBaseUrl
    ) {
        // Фильтр, который автоматически:
        //  - получает сервисный access token (client_credentials),
        //  - обновляет его,
        //  - добавляет в Authorization заголовок.
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        // Указываем client-id клиента из application.yml:
        // Именно этот клиент будет использоваться для получения токена.
        oauth2.setDefaultClientRegistrationId("transfer-service");

        return WebClient.builder()
                .baseUrl(accountsServiceBaseUrl) // базовый URL accounts-service
                .apply(oauth2.oauth2Configuration()) // подключаем OAuth2 фильтр
                .build();
    }*/

    @Bean
    @LoadBalanced
    public WebClient.Builder serviceClientBuilder(
            OAuth2AuthorizedClientManager authorizedClientManager/*,
            @Value("${bank.service.accounts.base-url}") String accountsServiceBaseUrl*/
    ) {
        // Фильтр, который автоматически:
        //  - получает сервисный access token (client_credentials),
        //  - обновляет его,
        //  - добавляет в Authorization заголовок.
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        // Указываем client-id клиента из application.yml:
        // Именно этот клиент будет использоваться для получения токена.
        oauth2.setDefaultClientRegistrationId("transfer-service");

        return WebClient.builder()
                //.baseUrl(accountsServiceBaseUrl) // базовый URL accounts-service
                .apply(oauth2.oauth2Configuration()) // подключаем OAuth2 фильтр
                //.build()
                ;
    }
}
