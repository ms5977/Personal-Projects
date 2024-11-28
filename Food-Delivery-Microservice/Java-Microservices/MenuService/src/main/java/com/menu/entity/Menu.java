package com.menu.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "Menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "item_name",length = 100,nullable = false)
    private String itemName;

    @Column(name = "item_description",columnDefinition = "TEXT")
    private String itemDescription;

    @Column(name = "item_price" ,precision = 10,scale = 2,nullable = false)
    private BigDecimal price;

    @Column(name = "available ",nullable = false)
    private Boolean available;

    @Column(name = "item_rating",precision = 2,scale = 1)
    @Min(0)
    @Max(5)
    private BigDecimal rating;

    private String image;
    @ManyToOne
    @JoinColumn(name = "dish_Id",nullable = false)
    private DishType dishType;

    @Column(name = "restaurant_Id",nullable = false)
    private Integer restaurantId;




}

