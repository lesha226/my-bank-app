package ru.yandex.practicum.mybank.service.account.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mybank.service.account.dto.balance.DepositRequest;
import ru.yandex.practicum.mybank.service.account.dto.balance.WithdrawRequest;
import ru.yandex.practicum.mybank.service.account.exception.InsufficientFundsException;
import ru.yandex.practicum.mybank.service.account.exception.LoginNotFoundException;
import ru.yandex.practicum.mybank.service.account.model.Account;
import ru.yandex.practicum.mybank.service.account.dto.balance.ServiceResponse;
import ru.yandex.practicum.mybank.service.account.dto.balance.TransferRequest;
import ru.yandex.practicum.mybank.service.account.repository.AccountRepository;

@Service
public class BalanceService {

    private final AccountRepository accountRepository;

    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ServiceResponse transfer(TransferRequest request) {
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
        return new ServiceResponse(info);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ServiceResponse deposit(DepositRequest request) {
        System.out.println("BalanceService.deposit request=" + request);
        if (request == null || Strings.isBlank(request.toLogin()) || request.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        baseDeposit(request.toLogin(), request.amount());

        String info = "Внесение выполнено: "
                + request.amount()
                + " на счёт " + request.toLogin();

        System.out.println("BalanceService.deposit info=" + info);
        return new ServiceResponse(info);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ServiceResponse withdraw(WithdrawRequest request) {
        System.out.println("BalanceService.withdraw request=" + request);
        if (request == null || Strings.isBlank(request.fromLogin()) || request.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        baseWithdraw(request.fromLogin(), request.amount());

        String info = "Снятие выполнено: "
                + request.amount()
                + " со счёта " + request.fromLogin();

        System.out.println("BalanceService.withdraw info=" + info);
        return new ServiceResponse(info);
    }

    private void baseDeposit(String toLogin, int amount) {
        Account toAccount = accountRepository.findByLogin(toLogin)
                .orElseThrow(() -> new LoginNotFoundException(toLogin));
        toAccount.setBalanceAmount(toAccount.getBalanceAmount() + amount);
        accountRepository.save(toAccount);
    }

    private void baseWithdraw(String fromLogin, int amount) {
        Account fromAccount = accountRepository.findByLogin(fromLogin)
                .orElseThrow(() -> new LoginNotFoundException(fromLogin));
        if (amount > fromAccount.getBalanceAmount()) {
            throw new InsufficientFundsException();
        }
        fromAccount.setBalanceAmount(fromAccount.getBalanceAmount() - amount);
        accountRepository.save(fromAccount);
    }
}
