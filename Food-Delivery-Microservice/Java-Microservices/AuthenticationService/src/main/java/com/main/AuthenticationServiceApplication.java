package com.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Authentication Service API",
				version = "1.0",
				description = """
                    The Authentication Service API handles user authentication, 
                    including user registration, login, token generation, 
                    and credential management.
                    """,
				contact = @Contact(
						name = "Manish Sharma",
						email = "mani.shiv21@gmail.com",
						url = "https://linkedin.com/in/manish-sharma"
				),
				license = @License(
						name = "MIT License",
						url = "https://opensource.org/licenses/MIT"
				)
		)
)
public class AuthenticationServiceApplication {
	private static  final Logger logger=LoggerFactory.getLogger(AuthenticationServiceApplication.class);
	public static void main(String[] args) {
		logger.info("Authentication Service is starting");
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
}
