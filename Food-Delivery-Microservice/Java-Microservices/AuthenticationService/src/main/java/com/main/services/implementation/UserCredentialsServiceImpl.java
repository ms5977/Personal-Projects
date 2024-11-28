package com.main.services.implementation;

import com.main.dto.UserCredentialRequest;
import com.main.dto.UserCredentialResponse;
import com.main.entity.Role;
import com.main.entity.UserCredential;
import com.main.exception.ResourceNotFoundException;
import com.main.repository.RoleRepo;
import com.main.repository.UserCredentialRepo;
import com.main.security.JwtService;
import com.main.services.UserCredentialsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {
    private final ModelMapper modelMapper;
    private final UserCredentialRepo userCredentialRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepo roleRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserCredentialsServiceImpl.class);

    public UserCredentialsServiceImpl(ModelMapper modelMapper, UserCredentialRepo userCredentialRepo, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepo roleRepo) {
        this.modelMapper = modelMapper;
        this.userCredentialRepo = userCredentialRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserCredentialResponse saveUser(UserCredentialRequest userCredentialRequest) {
        logger.info("Starting user registration process for username: {}", userCredentialRequest.getUsername());
        UserCredential userCredential = modelMapper.map(userCredentialRequest, UserCredential.class);
        userCredential.setPassword(passwordEncoder.encode(userCredentialRequest.getPassword()));
        Set<Role> roles = roleRepo.findByNameIn(userCredentialRequest.getRoles());
        logger.debug("Fetching roles: {}", userCredentialRequest.getRoles());
        userCredential.setRoles(roles);
        UserCredential savedCredential = userCredentialRepo.save(userCredential);
        logger.info("User successfully registered with username: {}", userCredentialRequest.getUsername());
        return modelMapper.map(savedCredential, UserCredentialResponse.class);
    }

    public String generateToken(String username) {
        logger.info("Generating JWT token for username: {}", username);
        String token = jwtService.generateToken(username);
        logger.info("JWT token successfully generated for username: {}", username);
        return token;
    }

    public void validateToken(String token) {
        logger.info("Validating token: {}", token);
        jwtService.validateToken(token);
        logger.info("Token validation successful.");
    }

    public void deleteUserCredential(String email) {
        logger.info("Initiating deletion of user credentials for email: {}", email);
        UserCredential userCredential = userCredentialRepo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found with given email: " + email);
                });

        userCredentialRepo.delete(userCredential);
        logger.info("User credentials successfully deleted for email: {}", email);
    }
}
