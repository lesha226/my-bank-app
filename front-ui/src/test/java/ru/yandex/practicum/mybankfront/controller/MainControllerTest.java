package ru.yandex.practicum.mybankfront.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.mybankfront.config.SecurityTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.*;
import ru.yandex.practicum.mybankfront.service.MainService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yandex.practicum.mybankfront.config.SecurityTestConfig.TEST_USER_USERNAME;

@WebMvcTest(MainController.class)
@Import(SecurityTestConfig.class)
@ActiveProfiles("test")
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MainService mainService;

    private final static MainResponse mainResponse = new MainResponse(
            "Иванов Иван",
            LocalDate.of(2001, 1, 1),
            100,
            List.of(
            new AccountDto("petrov", "Петров Петр"),
                    new AccountDto("sidorov", "Сидоров Сидор")
            ),
            List.of("error"), "info"
    );

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void getAccount_validUser_returnOk() throws Exception {
        when(mainService.getAccount(any())).thenReturn(mainResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/account")
                        /*.with(SecurityMockMvcRequestPostProcessors.oauth2Login()
                                .authorities(new SimpleGrantedAuthority("ROLE_USER")))*/
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", mainResponse.name()))
                .andExpect(MockMvcResultMatchers.model().attribute("birthdate", mainResponse.birthdate().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.model().attribute("sum", mainResponse.sum()))
                .andExpect(MockMvcResultMatchers.model().attribute("accounts", mainResponse.accounts()))
                .andExpect(MockMvcResultMatchers.model().attribute("errors", mainResponse.errors()))
                .andExpect(MockMvcResultMatchers.model().attribute("info", mainResponse.info()));

        verify(mainService).getAccount(any());
    }

    @Test
    void getAccount_anonymous_returnRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void editAccount_returnOk() throws Exception {
        String newName = "editedName";
        LocalDate newBirthdate = LocalDate.ofYearDay(1999,1);
        EditAccountRequest editAccountRequest = new EditAccountRequest(newName, newBirthdate);
        when(mainService.editAccount(any(), eq(editAccountRequest))).thenReturn(mainResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                        .param("name", newName)
                        .param("birthdate", newBirthdate.format(DateTimeFormatter.ISO_DATE))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", mainResponse.name()))
                .andExpect(MockMvcResultMatchers.model().attribute("birthdate", mainResponse.birthdate().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.model().attribute("sum", mainResponse.sum()))
                .andExpect(MockMvcResultMatchers.model().attribute("accounts", mainResponse.accounts()))
                .andExpect(MockMvcResultMatchers.model().attribute("errors", mainResponse.errors()))
                .andExpect(MockMvcResultMatchers.model().attribute("info", mainResponse.info()));

        verify(mainService).editAccount(any(), eq(editAccountRequest));
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void editCash_returnOk() throws Exception {
        EditCashRequest params = new EditCashRequest(100, CashAction.GET);
        when(mainService.editCash(any(), eq(params))).thenReturn(mainResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/cash")
                        .param("value", "100")
                        .param("action", "GET")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", mainResponse.name()))
                .andExpect(MockMvcResultMatchers.model().attribute("birthdate", mainResponse.birthdate().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.model().attribute("sum", mainResponse.sum()))
                .andExpect(MockMvcResultMatchers.model().attribute("accounts", mainResponse.accounts()))
                .andExpect(MockMvcResultMatchers.model().attribute("errors", mainResponse.errors()))
                .andExpect(MockMvcResultMatchers.model().attribute("info", mainResponse.info()));

        verify(mainService).editCash(any(), eq(params));
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void transfer() throws Exception {
        TransferRequest params = new TransferRequest(100, "user2");
        when(mainService.transfer(any(), eq(params))).thenReturn(mainResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .param("value", "100")
                        .param("recipient", "user2")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", mainResponse.name()))
                .andExpect(MockMvcResultMatchers.model().attribute("birthdate", mainResponse.birthdate().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.model().attribute("sum", mainResponse.sum()))
                .andExpect(MockMvcResultMatchers.model().attribute("accounts", mainResponse.accounts()))
                .andExpect(MockMvcResultMatchers.model().attribute("errors", mainResponse.errors()))
                .andExpect(MockMvcResultMatchers.model().attribute("info", mainResponse.info()));

        verify(mainService).transfer(any(), eq(params));
    }
}