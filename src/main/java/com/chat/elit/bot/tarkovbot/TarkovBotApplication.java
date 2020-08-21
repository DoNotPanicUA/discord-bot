package com.chat.elit.bot.tarkovbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TarkovBotApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TarkovBotApplication.class, args);
	}
}
