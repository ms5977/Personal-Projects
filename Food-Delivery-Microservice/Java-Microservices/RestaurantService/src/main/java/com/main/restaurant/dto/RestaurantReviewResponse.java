package com.main.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantReviewResponse {
    private Integer  restaurantId;
    private BigDecimal rating;
    private String name;
}
