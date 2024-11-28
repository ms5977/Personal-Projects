package com.menu.client;

import com.menu.dto.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANTSERVICE")
public interface RestaurantService {
    @GetMapping("/api/restaurant/restaurantId/{restaurantId}")
    RestaurantResponse getRestaurantById(@PathVariable Integer restaurantId);
}
