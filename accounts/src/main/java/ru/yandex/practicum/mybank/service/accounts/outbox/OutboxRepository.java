package ru.yandex.practicum.mybank.service.accounts.outbox;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends CrudRepository<Outbox, Long>, ListPagingAndSortingRepository<Outbox, Long> {
}
