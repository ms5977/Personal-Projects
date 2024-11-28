package com.main.customer.controllers;

import com.main.customer.dto.CustomPage;
import com.main.customer.services.CustomerService;
import com.main.customer.dto.CustomerRequest;
import com.main.customer.dto.CustomerResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
@Tag(name = "Customer API", description = "Endpoints for customer management")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomPage<CustomerResponse> mapToCustomPage(Page<CustomerResponse> page) {
        CustomPage<CustomerResponse> customPage = new CustomPage<>();
        customPage.setContent(page.getContent());
        customPage.setPageNumber(page.getNumber());
        customPage.setPageSize(page.getSize());
        customPage.setTotalElements(page.getTotalElements());
        customPage.setTotalPages(page.getTotalPages());
        return customPage;
    }

    // ------------------------- Create Customer -------------------------
    @Operation(summary = "Register a new Customer", description = "Register a new customer and returns the details")
    @PostMapping("/create")
    @CircuitBreaker(name = "AuthenticationServiceBreaker",fallbackMethod = "customerRegisterFallback")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        logger.info("Received request to create a new customer: {}", customerRequest);
        CustomerResponse createUser = customerService.createCustomer(customerRequest);
        logger.info("Successfully created customer with ID: {}", createUser);
        return new ResponseEntity<>(createUser, HttpStatus.OK);
    }

    // ---------------------- Get Customer By ID -------------------------
    @Operation(summary = "Get a Customer by ID", description = "Retrieve a customer's details with unique ID")
    @GetMapping("/customerId/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId) {
        logger.info("Received request to fetch customer with ID: {}", customerId);
        CustomerResponse customerByID = customerService.getCustomerByID(customerId);
        logger.info("Successfully fetched customer details for ID: {}", customerId);
        return new ResponseEntity<>(customerByID, HttpStatus.OK);
    }

    // ---------------------- Get All Customers -------------------------
    @Operation(summary = "Get All Customers", description = "Retrieve a paginated list of all customers")
    @GetMapping("/")
    public ResponseEntity<CustomPage<CustomerResponse>> getAllCustomer(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Received request to fetch all customers - Page Number: {}, Page Size: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CustomerResponse> page = customerService.getAllCustomer(pageable);
        CustomPage<CustomerResponse> customPage = mapToCustomPage(page);
        logger.info("Successfully fetched {} customers. Total Pages: {}, Total Elements: {}",
                customPage.getContent().size(), customPage.getTotalPages(), customPage.getTotalElements());
        return new ResponseEntity<>(customPage, HttpStatus.OK);
    }

    // ---------------------- Update Customer By Email -------------------------
    @Operation(summary = "Update Customer by Email", description = "Update a customer's details using email address.")
    @PutMapping("/update/{email}")
    public ResponseEntity<CustomerResponse> updateCustomerByEmail(@Valid @RequestBody CustomerRequest customerDTO, @PathVariable String email) {
        logger.info("Received request to update customer with email: {}", email);
        CustomerResponse updatedCustomer = customerService.updateByEmail(email, customerDTO);
        logger.info("Successfully updated customer with email: {}", email);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // ---------------------- Get Customer By Email -------------------------
    @Operation(summary = "Get Customer by Email", description = "Retrieve a customer's details using their email address.")
    @GetMapping("/getUser/{email}")
    public ResponseEntity<CustomerResponse> getCustomerbyEmail(@PathVariable String email) {
        logger.info("Received request to fetch customer with email: {}", email);
        CustomerResponse customerByEmail = customerService.getUserByEmail(email);
        logger.info("Successfully fetched customer details for email: {}", email);
        return new ResponseEntity<>(customerByEmail, HttpStatus.FOUND);
    }

    // ---------------------- Delete Customer By Email -------------------------
    @Operation(summary = "Delete Customer by Email", description = "Delete a customer's record using their email address.")
    @DeleteMapping("/{email}")
    @CircuitBreaker(name = "AuthenticationServiceDeleteBreaker",fallbackMethod = "deleteCustomerCredentialFallback")
    public ResponseEntity<String> deleteCustomerByEmail(@PathVariable String email) {
        logger.info("Received request to delete customer with email: {}", email);
        try {
            customerService.deleteCustomerByEmail(email);
            logger.info("Successfully deleted customer with email: {}", email);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting customer with email: {}. Error: {}", email, e.getMessage());
            return new ResponseEntity<>("Error deleting: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
//    -----------------------FallbackMethods-----------------------------------------------------
    public ResponseEntity<String>customerRegisterFallback(CustomerRequest customerRequest ,Throwable throwable){
        logger.error("Fallback method triggered due to:{}",throwable.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Authentication Service is temporarily unavailable.Please try again later!!");
    }
    public ResponseEntity<String>deleteCustomerCredentialFallback(String email,Throwable throwable){
        logger.error("Fallback method triggered due to:{}",throwable.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Authentication Service is temporarily unavailable.Please try again later!!");
    }
}
