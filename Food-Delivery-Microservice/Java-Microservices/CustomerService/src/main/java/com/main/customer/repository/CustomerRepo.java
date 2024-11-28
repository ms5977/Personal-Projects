package com.main.customer.repository;
import java.util.Optional;
import com.main.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepo  extends JpaRepository<Customer, Integer>{
	public Optional<Customer> findByEmail(String email);
	boolean existsByPhoneNumber(String integer);
}
