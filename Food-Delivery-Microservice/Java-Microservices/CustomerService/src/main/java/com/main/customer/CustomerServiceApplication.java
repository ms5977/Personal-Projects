package com.main.customer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Customer Service application.
 * This class bootstraps the application and provides OpenAPI documentation.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Customer API",
				version = "1.0",
				description = """
                        A comprehensive API for managing customer operations, 
                        including registration, retrieval, updates, and deletion.
                        """,
				contact = @Contact(
						name = "Manish Sharma",
						url = "https://linkedin.com/in/manish-sharma", // Add your LinkedIn or portfolio URL
						email = "mani.shiv21@gmail.com"
				),
				license = @License(
						name = "Terms & Conditions",
						url = "https://yourcompany.com/terms-and-conditions" // Replace with actual URL
				)
		)
)
public class CustomerServiceApplication {

	/**
	 * Entry point of the Customer Service application.
	 *
	 * @param args Command-line arguments.
	 */
	private static  final Logger logger =LoggerFactory.getLogger(CustomerServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
		logger.info("Customer Service Application started successfully!");
	}
}
