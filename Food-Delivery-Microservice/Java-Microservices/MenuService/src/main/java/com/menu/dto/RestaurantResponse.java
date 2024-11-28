package com.menu.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantResponse {
    private Integer restaurantId;
    private String name;
}
