package com.main.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialRequest {
    private String email;
    private String username;
    private String password;
    private List<String> roles; // Include roles here

    // Constructor
    public UserCredentialRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = List.of("USER_ROLE"); // Default role
    }

}
