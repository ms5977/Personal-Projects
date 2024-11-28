package com.main.customer.entity;

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
public class Address {
    private String houseNumber;
    private String city;
    private String area;
    private Long zipCode;

}

