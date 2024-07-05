package com.programmingtechie.inventoryservice;

import com.programmingtechie.inventoryservice.models.Inventory;
import com.programmingtechie.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	@Profile("default")
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			inventoryRepository.save(Inventory.builder()
											  .skuCode("iphone_13")
											  .quantity(100)
											  .build());
			inventoryRepository.save(Inventory.builder()
											  .skuCode("iphone_13_red")
											  .quantity(0)
											  .build());
		};
	}
	
}
