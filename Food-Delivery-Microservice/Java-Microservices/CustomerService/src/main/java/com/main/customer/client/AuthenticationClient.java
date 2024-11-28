package com.main.customer.client;

import com.main.customer.dto.CustomPage;
import com.main.customer.dto.CustomerResponse;
import com.main.customer.dto.UserCredentialRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTHENTICATIONSERVICE")
public interface AuthenticationClient {
    @PostMapping("/api/auth/register")
    void customerRegister(@RequestBody UserCredentialRequest userCredentialRequest);

    @DeleteMapping("/api/auth/delete/email/{email}")
    void deleteCustomerCredential(@PathVariable String email);
}
