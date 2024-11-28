package com.menu;

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
				title = "Restaurant Menu Service API",
				version = "1.0",
				description = """
                Welcome to the Restaurant Menu Service API!
                This API is designed to empower restaurants with the tools to manage their menu offerings, enhance customer interaction, and streamline operations. 
                Key Features include:
                
                - Manage Menus: Create, update, and delete restaurant menus effortlessly.
                - Menu Review System: Customers can rate and review dishes, improving restaurant services.
                - Advanced Search & Filtering: Search and filter menus based on location, ratings, and more.
                - Seamless Integration: Connects easily with other restaurant management services.
                
                For inquiries or support, feel free to contact the API creator:
                - **Manish Sharma**
                - LinkedIn: [Manish Sharma](https://linkedin.com/in/manish-sharma)
                - Email: [mani.shiv21@gmail.com](mailto:mani.shiv21@gmail.com)
                
                Thank you for using the Restaurant Menu Service API!
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
public class MenuServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MenuServiceApplication.class, args);
	}
}
