package com.dencooper.pnstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class PnstoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(PnstoreApplication.class, args);
	}
}
