package ru.yandex.practicum.mybankfront.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.mybankfront.controller.dto.EditAccountRequest;
import ru.yandex.practicum.mybankfront.controller.dto.EditCashRequest;
import ru.yandex.practicum.mybankfront.controller.dto.MainResponse;
import ru.yandex.practicum.mybankfront.controller.dto.TransferRequest;
import ru.yandex.practicum.mybankfront.service.MainService;

import java.time.format.DateTimeFormatter;

/**
 * Контроллер main.html.
 *
 * Используемая модель для main.html:
 *      model.addAttribute("name", name);
 *      model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ISO_DATE));
 *      model.addAttribute("sum", sum);
 *      model.addAttribute("accounts", accounts);
 *      model.addAttribute("errors", errors);
 *      model.addAttribute("info", info);
 *
 * Поля модели:
 *      name - Фамилия Имя текущего пользователя, String (обязательное)
 *      birthdate - дата рождения текущего пользователя, String в формате 'YYYY-MM-DD' (обязательное)
 *      sum - сумма на счету текущего пользователя, Integer (обязательное)
 *      accounts - список аккаунтов, которым можно перевести деньги, List<AccountDto> (обязательное)
 *      errors - список ошибок после выполнения действий, List<String> (не обязательное)
 *      info - строка успешности после выполнения действия, String (не обязательное)
 *
 * С примерами использования можно ознакомиться в тестовом классе заглушке AccountStub
 */
@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /**
     * GET /.
     * Редирект на GET /account
     */
    @GetMapping
    public String index() {
        return "redirect:/account";
    }

    /**
     * GET /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для получения данных аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     */
    @GetMapping("/account")
    public String getAccount(Model model, @AuthenticationPrincipal OidcUser user) {
        System.out.println("MainController.getAccount user=" + user);

        MainResponse response = mainService.getAccount(user);

        fillModel(model, response);

        return "main";
    }

    /**
     * POST /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для изменения данных текущего пользователя по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Изменяемые данные:
     * 1. name - Фамилия Имя
     * 2. birthdate - дата рождения в формате YYYY-DD-MM
     */
    @PostMapping("/account")
    public String editAccount(
            Model model,
            @AuthenticationPrincipal OidcUser user,
            @Valid EditAccountRequest params
    ) {
        System.out.println("MainController.editAccount user=" + user + ", params=" + params);

        MainResponse response = mainService.editAccount(user, params);

        fillModel(model, response);

        return "main";
    }

    /**
     * POST /cash.
     * Что нужно сделать:
     * 1. Сходить в сервис cash через Gateway API для снятия/пополнения счета текущего аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. action - GET (снять), PUT (пополнить)
     */
    @PostMapping("/cash")
    public String editCash(
            Model model,
            @AuthenticationPrincipal OidcUser user,
            @Valid EditCashRequest params
    ) {
        System.out.println("MainController.editCash user=" + user + ", params=" + params);

        MainResponse response = mainService.editCash(user, params);

        fillModel(model, response);

        return "main";
    }

    /**
     * POST /transfer.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для перевода со счета текущего аккаунта на счет другого аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     *
     * Параметры:
     * 1. value - сумма списания
     * 2. login - логин пользователя получателя
     */
    @PostMapping("/transfer")
    public String transfer(
            Model model,
            @AuthenticationPrincipal OidcUser user,
            @Valid TransferRequest params
    ) {
        System.out.println("MainController.transfer user=" + user + ", params=" + params);

        MainResponse response = mainService.transfer(user, params);

        fillModel(model, response);

        return "main";
    }

    private void fillModel(Model model, MainResponse mainResponse) {

        model.addAttribute("name", mainResponse.name());
        model.addAttribute("birthdate", mainResponse.birthdate().format(DateTimeFormatter.ISO_DATE));
        model.addAttribute("sum", mainResponse.sum());
        model.addAttribute("accounts", mainResponse.accounts());
        model.addAttribute("errors", mainResponse.errors());
        model.addAttribute("info", mainResponse.info());

    }
}
