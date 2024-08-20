package com.Nelson.product_service;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);


	}
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Dotenv dotenv = Dotenv.load();

			for (DotenvEntry entry : dotenv.entries()) {
				String key = entry.getKey();
				String value = entry.getValue();

				if (System.getProperty(key) == null) {
					System.setProperty(key, value);
				}
			}
		};
	}

	}
