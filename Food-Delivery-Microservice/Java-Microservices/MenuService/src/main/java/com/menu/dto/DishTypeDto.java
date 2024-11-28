package com.menu.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representation of a dish type, including category, description, and associated menu items")
public class DishTypeDto {

    @Schema(description = "Unique identifier for the dish type", example = "10")
    private Long dishId;

    @Schema(description = "Category of the dish", example = "Pizza")
    @NotBlank(message = "Dish type is required.")
    private String dishCategory;

    @Schema(description = "Description of the dish type", example = "A variety of baked flatbreads with toppings")
    @NotBlank(message = "Dish description is required.")
    private String dishDescription;

}
