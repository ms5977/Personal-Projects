package com.main.controller;

import com.main.dto.AuthRequest;
import com.main.dto.UserCredentialRequest;
import com.main.dto.UserCredentialResponse;
import com.main.services.implementation.UserCredentialsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserCredentialsServiceImpl userCredentialsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Register a new user", description = "Register a new user by providing username, password, and email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    @PostMapping("/register")
    public ResponseEntity<UserCredentialResponse> createUser(@RequestBody UserCredentialRequest userCredentialRequest) {
        logger.info("Initiating user registration for username: {}", userCredentialRequest.getUsername());
        UserCredentialResponse userCredentialResponse = userCredentialsService.saveUser(userCredentialRequest);
        logger.info("User registration successful for username: {}", userCredentialRequest.getUsername());
        return new ResponseEntity<>(userCredentialResponse, HttpStatus.OK);
    }

    @Operation(summary = "Generate authentication token", description = "Generate a token for authentication using username or email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, token generated."),
            @ApiResponse(responseCode = "400", description = "Invalid username/email or password.")
    })
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        logger.info("Initiating token generation for user.");
        try {
            Authentication authenticate;
            String token;
            if (authRequest.getUsername() != null) {
                logger.info("Authenticating using username: {}", authRequest.getUsername());
                authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                token = userCredentialsService.generateToken(authRequest.getUsername());
            } else if (authRequest.getEmail() != null) {
                logger.info("Authenticating using email: {}", authRequest.getEmail());
                authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
                token = userCredentialsService.generateToken(authRequest.getEmail());
            } else {
                logger.warn("Authentication failed: Username or email is null");
                return ResponseEntity.badRequest().body("Invalid access");
            }

            if (authenticate.isAuthenticated()) {
                logger.info("Authentication successful. Token generated.");
                return ResponseEntity.ok().body(token);
            } else {
                logger.warn("Authentication failed for user.");
                return ResponseEntity.badRequest().body("Invalid access");
            }
        } catch (Exception ex) {
            logger.error("Error during token generation: {}", ex.getMessage());
            return ResponseEntity.badRequest().body("Authentication error");
        }
    }

    @Operation(summary = "Validate a token", description = "Validate the provided authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid."),
            @ApiResponse(responseCode = "400", description = "Invalid token.")
    })
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        logger.info("Validating token: {}", token);
        userCredentialsService.validateToken(token);
        logger.info("Token validation successful.");
        return ResponseEntity.ok().body("Token is valid");
    }

    @Operation(summary = "Delete user credentials", description = "Delete the user credentials based on the provided email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User credentials deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<String> deleteUserCredentials(@Parameter(description = "Email of the user whose credentials need to be deleted") @PathVariable String email) {
        logger.info("Initiating deletion of credentials for email: {}", email);
        userCredentialsService.deleteUserCredential(email);
        logger.info("Credentials successfully deleted for email: {}", email);
        return ResponseEntity.ok().body("Credential Deleted Successfully");
    }
}
