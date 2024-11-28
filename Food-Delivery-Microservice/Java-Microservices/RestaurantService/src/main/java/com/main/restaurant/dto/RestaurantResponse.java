package com.main.restaurant.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantResponse {
    private Integer restaurantId;
    private String name;
    private String phoneNumber;
    private String email;
    private String image;
    private BigDecimal rating;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Set<String> cuisineTypes;
    private String closingHours;
    private String openingHours;
    private AddressDto address;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuResponse> menuResponse=new ArrayList<>();

    }

