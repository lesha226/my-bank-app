package ru.yandex.practicum.mybank.service.account.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.mybank.service.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByLogin(String login);

    List<Account> findAllByLoginNot(String login);

    //@Modifying
    @Query("update accounts set balance_amount = balance_amount + :amount where id = :id")
    int deposit(Long id, int amount);


    @Query("update accounts set balance_amount = balance_amount + :amount where id = :id")
    void deposit1(Long id, int amount);

    @Modifying
    @Query("update accounts a set balance_amount = a.balance_amount - :amount where a.id = :id and a.balance_amount >= :amount")
    long withdraw(Long id, int amount);
}
