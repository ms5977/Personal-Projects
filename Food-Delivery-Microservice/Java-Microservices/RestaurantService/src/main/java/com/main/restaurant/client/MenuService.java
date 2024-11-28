package com.main.restaurant.client;

import com.main.restaurant.dto.MenuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//url = "http://localhost:8083/api/menu"
@FeignClient(name = "MENUSERVICE")
public interface MenuService {

    @DeleteMapping("/api/menu/delete/restaurantId/{restaurantId}")
    List<MenuResponse> deleteMenuByRestaurantId(@PathVariable Integer restaurantId );
    @GetMapping("/api/menu/getByRestaurantId/{restaurantId}")
    List<MenuResponse> getMenuByRestaurantId(
            @PathVariable Integer restaurantId
    );
}
