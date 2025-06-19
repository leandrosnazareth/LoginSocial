package com.leandrosnazareth.LoginSocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LoginSocialApplication {

	public static void main(String[] args) {
		// Carrega o .env
		Dotenv dotenv = Dotenv.load();

		// Passa as variáveis para o System properties
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
		SpringApplication.run(LoginSocialApplication.class, args);
	}

}
