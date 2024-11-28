package com.menu.repository;

import com.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
    Page<Menu> findByItemNameIgnoreCaseContaining(String itemName, Pageable pageable);

    Page<Menu> findByRatingGreaterThanEqual(BigDecimal rating, Pageable pageable);

    Page<Menu> findByItemNameIgnoreCaseContainingAndRatingGreaterThanEqual(String itemName, BigDecimal rating, Pageable pageable);

    //    Page<Menu> findByRestaurantId(Integer restaurantId,Pageable pageable);
    List<Menu> findByRestaurantId(Integer restaurantId);
//    @Query("SELECT m FROM Menu m WHERE m.restaurantId = :restaurantId")
//    List<MenuProjection> findMenuByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Modifying
    @Query("DELETE from Menu m where m.restaurantId=:restaurantId")
    int deleteByRestaurantId(@Param("restaurantId") Integer restaurantId);
}
