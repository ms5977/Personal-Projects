package com.main.restaurant.services;
import com.main.restaurant.dto.RestaurantRequest;
import com.main.restaurant.dto.RestaurantResponse;
import com.main.restaurant.dto.RestaurantReviewRequest;
import com.main.restaurant.dto.RestaurantReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface RestaurantService {
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest);
    public Page<RestaurantResponse>getAllRestaurant(Pageable pageable);
    public Page<RestaurantResponse>  getRestaurantByName(String name, Pageable pageable);
    public  Page<RestaurantResponse>getRestaurantByCity(String city, Pageable pageable);
    public RestaurantResponse updateRestaurantDetails(Integer restaurantId, RestaurantRequest restaurantRequest);
    public void deleteRestaurantByEmail(String email);
    Page<RestaurantResponse>getRestaurantsByRating(Double rating, Pageable pageable);
    Page<RestaurantResponse>getRestaurantsByCityAndRating(String city, Double rating, Pageable pageable);
    public RestaurantResponse getRestaurantByEmail(String email);
    public Page<RestaurantResponse>getRestaurantByCuisine(String cuisine, Pageable pageable);
    public RestaurantResponse FindRestaurantById(Integer id);
    public ResponseEntity<String> deleteRestaurantById(Integer restaurantId);
    RestaurantReviewResponse updateRestaurantReviewById(Integer restaurantId, BigDecimal rating);
    RestaurantReviewResponse findRestaurantReviewById(Integer restaurantId);
}
