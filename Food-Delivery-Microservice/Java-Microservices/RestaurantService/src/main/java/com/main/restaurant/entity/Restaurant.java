package com.main.restaurant.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id",nullable = false)
    private Integer  restaurantId;

    @Column(name = "restaurant_name",nullable = false)
    private String name;

    @Column(name = "restaurant_phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "restaurant_email",unique = true,nullable = false)
    private String email;

    @Column(name = "restaurant_rating", precision = 2,scale = 1,nullable = false)
    @Min(0)
    @Max(5)
    private BigDecimal rating;

    @Column(name = "restaurant_closing_hour",length = 50,nullable = false)
    private String closingHours;

    @Column(name = "restaurant_opening_hours", length = 50 ,nullable = false)
    private String openingHours;

    @ElementCollection
    @CollectionTable(name = "restaurant_cuisine",joinColumns = @JoinColumn(name = "restaurantId"))
    private Set<String > cuisineTypes;
    @Column(name = "restaurant_description" ,nullable = false)
    private String description;
    private String image;
    @Embedded
    private ResturantAddress address;

}
