package com.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing a menu item.
 * This class is used to encapsulate the details of a menu item,
 * including its name, description, price, and availability status,
 * among other attributes.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for creating or updating a menu item")
public class MenuRequest {

    @Schema(description = "Unique identifier for the menu item (optional, for updates)", example = "101")
    private Long menuId;

    @Schema(description = "Name of the menu item", example = "Margherita Pizza")
    @NotBlank(message = "Item name is required.")
    @Size(max = 100, message = "Item name cannot exceed 100 characters.")
    private String itemName;

    @Schema(description = "Detailed description of the menu item", example = "Classic Italian pizza with mozzarella cheese and fresh basil.")
    @Size(max = 1000, message = "Item description cannot exceed 1000 characters.")
    private String itemDescription;

    @Schema(description = "Price of the menu item", example = "12.99")
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    @DecimalMax(value = "9999999999.99", message = "Price must not exceed 9999999999.99.")
    private BigDecimal price;

    @Schema(description = "Availability status of the menu item", example = "true")
    @NotNull(message = "Availability is required.")
    private Boolean available;

    @Schema(description = "URL or reference to the image of the menu item", example = "https://example.com/images/pizza.jpg")
    private String image;

    @Schema(description = "Dish type information for categorizing the menu item", example = "{\"id\": 1, \"name\": \"Pizza\"}")
    private DishTypeDto dishType;

    @Schema(description = "Identifier of the restaurant this menu item belongs to", example = "5")
    private Integer restaurantId;

}
