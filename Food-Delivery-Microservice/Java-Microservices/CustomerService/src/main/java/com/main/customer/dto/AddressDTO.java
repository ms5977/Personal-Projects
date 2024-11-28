package com.main.customer.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
public class AddressDTO {

    @NotBlank(message = "House number should not be blank")
    @Size(max = 10, message = "House number should not exceed 10 characters")
    private String houseNumber;

    @NotBlank(message = "Area should not be blank")
    @Size(max = 50, message = "Area should not exceed 50 characters")
    private String area;

    @NotBlank(message = "City should not be blank")
    @Size(max = 50, message = "City should not exceed 50 characters")
    private String city;

    @Min(value = 10000, message = "Zip code should be at least 5 digits long")
    @Max(value = 999999, message = "Zip code should not exceed 6 digits")
    private Long zipCode;
}

