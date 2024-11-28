package com.main.customer.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerResponse {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private AddressDTO location;
    private String image;
    private String username;

}

