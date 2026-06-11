package ru.yandex.practicum.mybank.service.account.outbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("outbox")
public class Outbox {
    @Id
    @Column("id")
    private Long id;

    @Column("src")
    private String src;

    @Column("login")
    private String login;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("body")
    private String body;
}
