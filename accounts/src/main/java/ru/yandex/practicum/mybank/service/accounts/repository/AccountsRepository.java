package ru.yandex.practicum.mybank.service.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.mybank.service.accounts.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByLogin(String login);

    List<Account> findAllByLoginNot(String login);

}
