package ru.yandex.practicum.mybank.service.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column("id")
    private Long id;

    @Column("login")
    private String login;

    @Column("name")
    private String name;

    @Column("birthdate")
    private LocalDate birthdate;

    @Column("balance_amount")
    private int balanceAmount;
}
