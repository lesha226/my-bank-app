package ru.yandex.practicum.mybank.service.accounts.exception;

public class InsufficientFundsException extends IllegalArgumentException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
