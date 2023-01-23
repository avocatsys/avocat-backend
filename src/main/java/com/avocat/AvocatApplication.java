package com.avocat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controller","service","entity", "repository"})
public class AvocatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvocatApplication.class, args);
	}

}
