package com.main.customer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Request object for registering a customer")
public class CustomerRequest {

    @Schema(description = "Customer's username ",example = "Ram")
    @NotBlank(message = "Name must not be empty")
    private String name;

    @Schema(description = "Customer email address",example = "Ram.shy21@gmail.com")
    @NotBlank(message = "Email must not be empty")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Please provide a valid email address"
    )
    private String email;

    @Schema(description = "Customer's password",example = "Pass@word123")
    @NotBlank(message = "Please enter the password")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
    )
    private String password;

    @Schema(description = "Customer's phone number",example = "9873023232")
    @NotBlank(message = "Phone Number should not be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone Number must be a valid 10-digit number"
    )
    private String phoneNumber;

    @Schema(description = "Customer's location",example = "Delhi,India")
    @Valid
    @NotNull(message = "Address should not be null")
//    @JsonProperty("Address")
    private AddressDTO location;
    private String image;

    @Schema(description = "Customer's username",example = "Ram112")
    private String username;
}

