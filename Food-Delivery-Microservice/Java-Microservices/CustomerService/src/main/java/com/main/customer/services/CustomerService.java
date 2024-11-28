package com.main.customer.services;
import com.main.customer.dto.CustomerRequest;
import com.main.customer.dto.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
	public CustomerResponse createCustomer(CustomerRequest customerDTO);
	public Page<CustomerResponse> getAllCustomer(Pageable pageable);
	public CustomerResponse updateByEmail(String email, CustomerRequest userDto);
	public CustomerResponse getUserByEmail(String email);
	public void deleteCustomerByEmail(String email);
	public CustomerResponse getCustomerByID(Integer customerId);

}
