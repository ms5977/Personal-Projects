package com.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user credentials")
public class UserCredentialRequest {

    @Schema(description = "Username of the user", example = "manish123")
    @NotBlank(message = "Username is required.")
    private String username;

    @Schema(description = "Email address of the user", example = "manish123@example.com")
    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Schema(description = "Password for the user account", example = "P@ssw0rd")
    @NotBlank(message = "Password is required.")
    private String password;

    @Schema(description = "Roles assigned to the user", example = "[\"ADMIN\", \"USER\"]")
    @NotNull(message = "Roles must not be null.")
    private Set<String> roles;
}
