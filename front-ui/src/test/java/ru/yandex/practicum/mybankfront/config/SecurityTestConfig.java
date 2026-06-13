package ru.yandex.practicum.mybankfront.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
@Import(SecurityConfig.class)
public class SecurityTestConfig {

    public static final String TEST_USER_USERNAME = "testUserName";
    public static final TestUser TEST_USER = new TestUser();

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService mock = mock(UserDetailsService.class);
        when(mock.loadUserByUsername(TEST_USER_USERNAME)).thenReturn(TEST_USER);
        return mock;
    }

    public static class TestUser implements OidcUser, UserDetails {


        // from UserDetails
        @Override
        public Map<String, Object> getAttributes() {
            return Map.of();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }

        @Override
        public String getPassword() {
            return "";
        }

        @Override
        public String getUsername() {
            return TEST_USER_USERNAME;
        }

        // from OidcUser
        @Override
        public Map<String, Object> getClaims() {
            return Map.of();
        }

        @Override
        public OidcUserInfo getUserInfo() {
            return null;
        }

        @Override
        public OidcIdToken getIdToken() {
            return null;
        }

        @Override
        public String getName() {
            return TEST_USER_USERNAME;
        }
    }
}
