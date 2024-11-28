package com.main.restaurant;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Restaurant Service API",
				version = "1.0",
				description = """
                        A comprehensive API for managing restaurant operations, 
                        including restaurant listings, filtering by city and rating, 
                        retrieving reviews, and managing customer interactions.
                        """,
				contact = @Contact(
						name = "Manish Sharma",
						url = "https://linkedin.com/in/manish-sharma",
						email = "mani.shiv21@gmail.com"
				),
				license = @License(
						name = "Terms & Conditions",
						url = "https://yourcompany.com/terms-and-conditions"
				)
		)
)
public class RestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

}
