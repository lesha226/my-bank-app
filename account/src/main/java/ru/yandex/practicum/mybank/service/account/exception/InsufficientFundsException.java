package ru.yandex.practicum.mybank.service.account.exception;

public class InsufficientFundsException extends IllegalArgumentException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
