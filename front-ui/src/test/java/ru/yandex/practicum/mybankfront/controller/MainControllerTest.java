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
import ru.yandex.practicum.mybankfront.config.SecurityTestConfig;
import ru.yandex.practicum.mybankfront.controller.dto.*;
import ru.yandex.practicum.mybankfront.service.MainService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.mybankfront.config.SecurityTestConfig.TEST_USER_USERNAME;

@WebMvcTest(MainController.class)
@Import(SecurityTestConfig.class)
@ActiveProfiles("test")
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MainService mainService;

    private final static List<AccountDto> accounts = List.of(
            new AccountDto("petrov", "Петров Петр"),
            new AccountDto("sidorov", "Сидоров Сидор"));
    private final static AccountResponse ACCOUNT_RESPONSE = new AccountResponse(
            "Иванов Иван","2001-1-1", 123, accounts, List.of("error"), "info");
    private final static ExecutionStatusResponse EXECUTION_RESULT = new ExecutionStatusResponse(List.of("error"), "info");

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void getAccount_validUser_returnOk() throws Exception {
        when(mainService.getAccountDetail(any(), eq(EXECUTION_RESULT))).thenReturn(ACCOUNT_RESPONSE);

        mockMvc.perform(MockMvcRequestBuilders.get("/account")
                                .flashAttr("errors", EXECUTION_RESULT.errors())
                                .flashAttr("info", EXECUTION_RESULT.info())
                                /*.with(SecurityMockMvcRequestPostProcessors.oauth2Login()
                                .authorities(new SimpleGrantedAuthority("ROLE_USER")))*/
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", ACCOUNT_RESPONSE.name()))
                .andExpect(model().attribute("birthdate", ACCOUNT_RESPONSE.birthdate()))
                .andExpect(model().attribute("sum", ACCOUNT_RESPONSE.sum()))
                .andExpect(model().attribute("accounts", ACCOUNT_RESPONSE.accounts()))
                .andExpect(model().attribute("errors", ACCOUNT_RESPONSE.errors()))
                .andExpect(model().attribute("info", ACCOUNT_RESPONSE.info()));

        verify(mainService).getAccountDetail(any(), eq(EXECUTION_RESULT));
    }

    @Test
    void getAccount_anonymous_returnRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void testEditAccount() throws Exception {
        String newName = "editedName";
        LocalDate newBirthdate = LocalDate.ofYearDay(1999,1);
        EditAccountRequest editAccountRequest = new EditAccountRequest(newName, newBirthdate);
        when(mainService.editAccount(any(), eq(editAccountRequest))).thenReturn(EXECUTION_RESULT);

        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                        .param("name", newName)
                        .param("birthdate", newBirthdate.format(DateTimeFormatter.ISO_DATE))
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/account"))
                .andExpect(flash().attribute("errors", EXECUTION_RESULT.errors()))
                .andExpect(flash().attribute("info", EXECUTION_RESULT.info()));

        verify(mainService).editAccount(any(), eq(editAccountRequest));
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void editCash_returnOk() throws Exception {
        EditCashRequest params = new EditCashRequest(100, CashAction.GET);
        when(mainService.editCash(any(), eq(params))).thenReturn(EXECUTION_RESULT);

        mockMvc.perform(MockMvcRequestBuilders.post("/cash")
                        .param("value", "100")
                        .param("action", "GET")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/account"))
                .andExpect(flash().attribute("errors", EXECUTION_RESULT.errors()))
                .andExpect(flash().attribute("info", EXECUTION_RESULT.info()));

        verify(mainService).editCash(any(), eq(params));
    }

    @Test
    @WithUserDetails(value = TEST_USER_USERNAME)
    void transfer() throws Exception {
        TransferRequest params = new TransferRequest(100, "user2");
        when(mainService.transfer(any(), eq(params))).thenReturn(EXECUTION_RESULT);

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .param("value", "100")
                        .param("recipient", "user2")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/account"))
                .andExpect(flash().attribute("errors", EXECUTION_RESULT.errors()))
                .andExpect(flash().attribute("info", EXECUTION_RESULT.info()));

        verify(mainService).transfer(any(), eq(params));
    }
}