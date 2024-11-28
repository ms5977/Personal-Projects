package com.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Payment Service API",
				version = "1.0",
				description = "API for managing payments, including creating payment orders and handling Razorpay webhooks."
		)
)
public class PaymentServiceApplication {

	public static void main(String[] args) {
		Logger logger= LoggerFactory.getLogger(PaymentServiceApplication.class);
		logger.info("Payment service server started");
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
