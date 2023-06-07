package com.web.farm.FarmShop;

import com.web.farm.FarmShop.config.StorageProperties;
import com.web.farm.FarmShop.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FarmShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmShopApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return(args->{
			storageService.init();
		});
	}
}
