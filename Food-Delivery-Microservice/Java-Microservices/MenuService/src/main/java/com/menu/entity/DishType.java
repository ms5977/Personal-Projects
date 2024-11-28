package com.menu.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DishType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishId;

    @Column(name = "dish_categories",nullable = false)
    private String dishCategory;

    @Column(name = "dish_description",nullable = false)
    private String dishDescription;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "dishType")
    private Set<Menu>menus;
}
