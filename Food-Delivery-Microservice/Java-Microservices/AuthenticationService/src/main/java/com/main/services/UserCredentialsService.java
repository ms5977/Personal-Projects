package com.main.services;

import com.main.dto.UserCredentialRequest;
import com.main.dto.UserCredentialResponse;
import com.main.entity.Role;

import java.util.Set;

public interface UserCredentialsService {
    UserCredentialResponse saveUser(UserCredentialRequest userCredentialRequest);
    public  void validateToken(String token);
    public void deleteUserCredential(String email);
}
