package com.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for authentication")
public class AuthRequest {

    @Schema(description = "Username of the user", example = "manish123")
    @NotBlank(message = "Username is required.")
    private String username;

    @Schema(description = "Password for the user account", example = "P@ssw0rd")
    @NotBlank(message = "Password is required.")
    private String password;

    @Schema(description = "Email address of the user", example = "manish123@example.com")
    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email is required.")
    private String email;
}
