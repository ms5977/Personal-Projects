package com.main.restaurant.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Request object for registering or updating the restaurant")
public class RestaurantRequest {

    private Integer restaurantId;

    @Schema(description = "Restaurant's name",example = "Ram restaurant")
    @NotBlank(message = "Restaurant name must not be empty")
    @Size(max = 100, message = "Restaurant name must not exceed 100 characters")
    private String name;

    @Schema(description = "Restaurant's phone number",example = "987xxxxxxx")
    @NotBlank(message = "Phone number must not be empty")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be a valid 10-digit number"
    )
    private String phoneNumber;  // Make this unique at the DB level

    @Schema(description = "Restaurant's email ",example = "Ram12@gmail.com")
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Please provide a valid email address")
    private String email;  // Make this unique at the DB level

    @Schema(description = "URL or the reference to the restaurant's image",example = "https://example.com/image.jpg")
    private String image;


    @Schema(description = "Restaurant's description ",example = "A cozy family restaurant serving traditional Indian cuisine.")
    @NotBlank(message = "Description must not be empty")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Schema(description = "Set of cuisine types offered by the restaurant",example = "[\"Indian\",\"Chinese\"]")
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    @NotNull(message = "Cuisine types must not be null")
    @Size(min = 1, message = "At least one cuisine type must be specified")
    private Set<String> cuisineTypes;

    @Schema(description = "Closing hours of the restaurant (24-hour format)",example = "22:30")
    @NotBlank(message = "Closing hours must not be empty")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Closing hours must be in HH:mm format (24-hour)")
    private String closingHours;

    @Schema(description = "Opening hours of the restaurant (24-hour format)",example = "09:00")
    @NotBlank(message = "Opening hours must not be empty")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Opening hours must be in HH:mm format (24-hour)")
    private String openingHours;

    @Schema(description = "Address of the restaurant ",example = "Delhi,India")
    @Valid
    @NotNull(message = "Address must not be null")
    @JsonProperty("location")
    private AddressDto address;



}
