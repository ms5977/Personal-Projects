package com.main.serviceRegistery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegiseryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegiseryApplication.class, args);
	}

}
