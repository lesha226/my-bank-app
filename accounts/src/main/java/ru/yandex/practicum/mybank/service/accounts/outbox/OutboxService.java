package ru.yandex.practicum.mybank.service.accounts.outbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mybank.service.accounts.outbox.NotificationClient;
import ru.yandex.practicum.mybank.service.accounts.outbox.OutboxRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutboxService {

    private final OutboxRepository repository;
    private final NotificationClient client;
    private final int pageSize;

    public OutboxService(
            OutboxRepository repository, NotificationClient client,
            @Value("${bank.service.notification.pageSize:10}") int pageSize
    ) {
        this.repository = repository;
        this.client = client;
        this.pageSize = pageSize;
    }

    public void asyncNotify(Outbox outbox) {
        System.out.println("OutboxService.asyncNotify: outbox=" + outbox);
        try {
            repository.save(outbox);
        } catch (Exception e) {
            System.out.println("ERROR NotificationService.asyncNotify: " + e.getMessage());
        }
    }

    @Scheduled(fixedDelayString = "PT5s")
    private void procces() {
        Page<Outbox> page = repository.findAll(Pageable.ofSize(pageSize));

        List<Long> deleteList = new ArrayList<>();
        try {
            for (Outbox item : page) {
                System.out.println("OutboxService.procces: item=" + item);

                NotificationRequest request = toRequest(item);
                client.notify(request);
                deleteList.add(item.getId());
            }
        } catch (Exception e) {
            System.out.println("ERROR NotificationService.procces: " + e.getMessage());
        }

        repository.deleteAllById(deleteList);
    }

    private NotificationRequest toRequest(Outbox prm) {
        return new NotificationRequest(prm.getSrc(), prm.getLogin(), prm.getCreatedAt(), prm.getBody());
    }

}
