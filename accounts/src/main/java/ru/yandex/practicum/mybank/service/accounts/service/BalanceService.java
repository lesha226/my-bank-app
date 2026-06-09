package ru.yandex.practicum.mybank.service.accounts.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.accounts.exception.InsufficientFundsException;
import ru.yandex.practicum.mybank.service.accounts.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.accounts.model.Account;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.BalanceResponse;
import ru.yandex.practicum.mybank.service.accounts.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.accounts.repository.AccountsRepository;

@Service
public class BalanceService {

    private final AccountsRepository accountsRepository;

    public BalanceService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BalanceResponse transfer(TransferRequest request) {
        System.out.println("BalanceService.transfer request=" + request);
        if (request == null || Strings.isBlank(request.fromLogin()) || Strings.isBlank(request.toLogin()) || request.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        baseWithdraw(request.fromLogin(), request.amount());
        baseDeposit(request.toLogin(), request.amount());

        String info = "Перевод выполнен: "
                + request.amount()
                + " со счёта " + request.fromLogin()
                + " на счёт " + request.toLogin();

        System.out.println("BalanceService.transfer info=" + info);
        return new BalanceResponse(info);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BalanceResponse deposit(DepositRequest request) {
        System.out.println("BalanceService.deposit request=" + request);
        if (request == null || Strings.isBlank(request.toLogin()) || request.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        baseDeposit(request.toLogin(), request.amount());

        String info = "Внесение выполнено: "
                + request.amount()
                + " на счёт " + request.toLogin();

        System.out.println("BalanceService.deposit info=" + info);
        return new BalanceResponse(info);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BalanceResponse withdraw(WithdrawRequest request) {
        System.out.println("BalanceService.withdraw request=" + request);
        if (request == null || Strings.isBlank(request.fromLogin()) || request.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        baseWithdraw(request.fromLogin(), request.amount());

        String info = "Снятие выполнено: "
                + request.amount()
                + " со счёта " + request.fromLogin();

        System.out.println("BalanceService.withdraw info=" + info);
        return new BalanceResponse(info);
    }

    private void baseDeposit(String toLogin, int amount) {
        Account toAccount = accountsRepository.findByLogin(toLogin)
                .orElseThrow(() -> new LoginNotFoundException(toLogin));
        toAccount.setBalanceAmount(toAccount.getBalanceAmount() + amount);
        accountsRepository.save(toAccount);
    }

    private void baseWithdraw(String fromLogin, int amount) {
        Account fromAccount = accountsRepository.findByLogin(fromLogin)
                .orElseThrow(() -> new LoginNotFoundException(fromLogin));
        if (amount > fromAccount.getBalanceAmount()) {
            throw new InsufficientFundsException();
        }
        fromAccount.setBalanceAmount(fromAccount.getBalanceAmount() - amount);
        accountsRepository.save(fromAccount);
    }
}
