package com.main.customer.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer  extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id",nullable = false)
    private Integer userId;
    @Column(name = "customer_name",nullable = false)
    private String name;

    @Column(name = "customer_email",unique = true)
    private String email;

    @Column(name = "customer_password",nullable = false)
    private String password;

    @Column(name = "user_name",nullable = false)
    private String username;

    @Column(name = "customer_phone_number",nullable = false)
    private String phoneNumber;

    @Embedded
    @Column(name = "customer_location")
    private Address location;

    @Column(name = "customer_profile_image",nullable = false)
    private String image;
}
