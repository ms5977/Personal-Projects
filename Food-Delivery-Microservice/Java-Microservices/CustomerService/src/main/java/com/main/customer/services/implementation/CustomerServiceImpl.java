package com.main.customer.services.implementation;

import com.main.customer.client.AuthenticationClient;
import com.main.customer.dto.UserCredentialRequest;
import com.main.customer.services.CustomerService;
import com.main.customer.dto.AddressDTO;
import com.main.customer.dto.CustomerRequest;
import com.main.customer.dto.CustomerResponse;
import com.main.customer.entity.Address;
import com.main.customer.entity.Customer;
import com.main.customer.exception.DuplicateResourceException;
import com.main.customer.exception.ResourceNotFoundException;
import com.main.customer.repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing customers within the application.
 */

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;
    private final AuthenticationClient authenticationClient;

    public CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper, AuthenticationClient authenticationClient) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        logger.info("Attempting to create a new customer with phone number: {}", customerRequest.getPhoneNumber());

        // Check for duplicate phone number
        if (customerRepo.existsByPhoneNumber(customerRequest.getPhoneNumber())) {
            logger.error("Customer creation failed: Phone number {} already exists", customerRequest.getPhoneNumber());
            throw new DuplicateResourceException("Customer with phone number " + customerRequest.getPhoneNumber() + " already exists");
        }

        // Map request to entity
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        customer.setImage("default.png");

        // Save customer to database
        Customer savedCustomer = customerRepo.save(customer);
        logger.info("Customer successfully saved with ID: {}", savedCustomer);

        // Map saved customer to response DTO
        AddressDTO mappedAddress = modelMapper.map(savedCustomer.getLocation(), AddressDTO.class);
        CustomerResponse customerResponse = modelMapper.map(savedCustomer, CustomerResponse.class);
        customerResponse.setLocation(mappedAddress);

        // Call authentication service
        try {
            authenticationClient.customerRegister(
                    new UserCredentialRequest(
                            customerRequest.getUsername(),
                            customerRequest.getEmail(),
                            customerRequest.getPassword()
                    )
            );
            logger.info("Customer credentials registered with Authentication Service for email: {}", customerRequest.getEmail());
        } catch (Exception e) {
            logger.error("Failed to register customer credentials with Authentication Service for email: {}. Rolling back transaction.", customerRequest.getEmail());
            // Re-throw exception to trigger rollback
            throw new RuntimeException("Authentication Service failure: " + e.getMessage(), e);
        }

        return customerResponse;
    }

    @Override
    public Page<CustomerResponse> getAllCustomer(Pageable pageable) {
        logger.info("Fetching all customers with pagination - Page Number: {}, Page Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Customer> customers = customerRepo.findAll(pageable);
        logger.info("Successfully fetched {} customers", customers.getContent().size());
        return customers.map(customer -> modelMapper.map(customer, CustomerResponse.class));
    }

    @Override
    public CustomerResponse updateByEmail(String email, CustomerRequest customerDTO) {
        logger.info("Attempting to update customer with email: {}", email);

        Address address = modelMapper.map(customerDTO.getLocation(), Address.class);
        Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> {
            logger.error("Customer update failed: No customer found with email {}", email);
            return new ResourceNotFoundException("User not found with " + email);
        });

        customer.setName(customerDTO.getName());
        customer.setLocation(address);
        customer.setPassword(customerDTO.getPassword());
        if (customerDTO.getImage() != null) {
            customer.setImage(customerDTO.getImage());
        }

        Customer savedCustomer = customerRepo.save(customer);
        logger.info("Successfully updated customer with email: {}", email);
        return modelMapper.map(savedCustomer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse getUserByEmail(String email) {
        logger.info("Fetching customer details for email: {}", email);
        Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> {
            logger.error("Customer retrieval failed: No customer found with email {}", email);
            return new ResourceNotFoundException("User not found with " + email);
        });

        AddressDTO address = modelMapper.map(customer.getLocation(), AddressDTO.class);
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        customerResponse.setLocation(address);
        logger.info("Successfully fetched customer details for email: {}", email);
        return customerResponse;
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        logger.info("Attempting to delete customer with email: {}", email);
        Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> {
            logger.error("Customer deletion failed: No customer found with email {}", email);
            return new ResourceNotFoundException("User not found with that email: " + email);
        });

        customerRepo.delete(customer);
        logger.info("Successfully deleted customer with email: {}", email);
        authenticationClient.deleteCustomerCredential(email);
        logger.info("Deleted customer credentials for email: {}", email);
    }

    @Override
    public CustomerResponse getCustomerByID(Integer customerId) {
        logger.info("Fetching customer details for ID: {}", customerId);
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> {
            logger.error("Customer retrieval failed: No customer found with ID {}", customerId);
            return new ResourceNotFoundException("Customer not found with given id:" + customerId);
        });

        logger.info("Successfully fetched customer details for ID: {}", customerId);
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
