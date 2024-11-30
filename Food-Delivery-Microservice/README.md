# Food Delivery Microservices (Backend)

A scalable food delivery application built with a microservices architecture using Java Spring Boot and Node.js. The application provides essential services for customers, restaurants, delivery agents, and payment systems.

## Features

- **Microservices Architecture**: Includes Java Spring Boot and Node.js services for scalability.
- **Java Spring Boot Services**: AuthenticationService, CustomerService, MenuService, PaymentService, RestaurantService, ApiGateway, ServiceRegistry.
- **Node.js Services**: Order-Service, Delivery, DeliveryAgent, Review.
- **Logging**: Configured structured logging using SLF4J (Java) and Winston (Node.js).
- **Resilience**: Integrated Resilience4j for circuit breaking, fallback methods, and rate limiting.

## Tech Stack

- **Backend**: Java Spring Boot, Node.js, Express.js
- **Database**: MySQL, MongoDB
- **Authentication**: JWT
- **API Documentation**: Swagger (Java), OpenAPI (Node.js)
- **Service Discovery**: Netflix Eureka
- **Fault Tolerance**: Resilience4j

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/ms5977/Personal-Projects/tree/master/Food-Delivery-Microservice
   ```

2. Navigate to the project directory:

   ```bash
   cd Food-Delivery-Microservice
   ```

3. Set up and start each microservice as required.

## GitHub Repository

[https://github.com/ms5977/Personal-Projects/tree/master/Food-Delivery-Microservice](https://github.com/ms5977/Personal-Projects/tree/master/Food-Delivery-Microservice)

## Challenges and Future Improvements

- Implement centralized logging for better monitoring and debugging.
- Further enhance the resilience of the system by adding more fallback methods.
- Improve inter-service communication and API Gateway routing.

## License

This project is licensed under the MIT License.
