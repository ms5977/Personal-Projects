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
public class MenuResponse {
    private Long menuId;
    private String itemName;
    private String itemDescription;
    private BigDecimal price;
    private Boolean available;
    private BigDecimal rating;
}
