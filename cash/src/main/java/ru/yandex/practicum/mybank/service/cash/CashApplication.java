package ru.yandex.practicum.mybank.service.cash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CashApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashApplication.class, args);
	}

}
