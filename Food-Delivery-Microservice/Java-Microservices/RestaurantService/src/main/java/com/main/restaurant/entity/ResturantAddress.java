package com.main.restaurant.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class ResturantAddress {
    private String shopNumber;
    private String city;
    private String area;
    private Long zipCode;
}


