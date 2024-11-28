package com.main.restaurant.controllers;

import com.main.restaurant.dto.RestaurantReviewRequest;
import com.main.restaurant.dto.RestaurantReviewResponse;
import com.main.restaurant.services.RestaurantService;
import com.main.restaurant.dto.RestaurantRequest;
import com.main.restaurant.dto.RestaurantResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller class for managing restaurant-related operations.
 */
@RestController
@RequestMapping("api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    //    ------------------------------------create--------------------------------------------------
    @Operation(summary = "Create a new restaurant", description = "Create a new restaurant entry with the provided details.")
    @PostMapping("/create")
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody RestaurantRequest restaurantRequest) {
        logger.info("Entering createRestaurant method with request: {}", restaurantRequest);
        RestaurantResponse createdRestaurant = restaurantService.createRestaurant(restaurantRequest);
        logger.info("Restaurant created successfully: {}", createdRestaurant);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }


    //    ------------------------------------updateRestaurantDetails--------------------------------------------------
    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurantDetails(
            @PathVariable Integer restaurantId, @Valid @RequestBody RestaurantRequest restaurantRequest) {
        logger.info("Entering updateRestaurantDetails method for restaurantId: {}, with request: {}", restaurantId, restaurantRequest);
        RestaurantResponse updatedRestaurant = restaurantService.updateRestaurantDetails(restaurantId, restaurantRequest);
        logger.info("Restaurant updated successfully: {}", updatedRestaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    //    ------------------------------------updateRestaurantReviewById--------------------------------------------------
    @PutMapping("/update/review/restaurantId/{restaurantId}")
    public ResponseEntity<RestaurantReviewResponse> updateRestaurantReviewById(
            @PathVariable Integer restaurantId, @RequestParam(required = true) BigDecimal rating) {
        logger.info("Entering updateRestaurantReviewById method for restaurantId: {}, with rating: {}", restaurantId, rating);
        RestaurantReviewResponse restaurantReview = restaurantService.updateRestaurantReviewById(restaurantId, rating);
        logger.info("Restaurant review updated successfully: {}", restaurantReview);
        return new ResponseEntity<>(restaurantReview, HttpStatus.OK);
    }

    //    ------------------------------------deleteRestaurantById--------------------------------------------------
    @DeleteMapping("/delete/{restaurantId}")
    @CircuitBreaker(name = "MenuService",fallbackMethod = "deleteMenuByRestaurantIdFallback")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Integer restaurantId) {
        logger.info("Entering deleteRestaurantById method for restaurantId: {}", restaurantId);
        ResponseEntity<String> response = restaurantService.deleteRestaurantById(restaurantId);
        logger.info("Restaurant deleted successfully with restaurantId: {}", restaurantId);
        return response;
    }

    //    ------------------------------------get all restaturant--------------------------------------------------
    @Operation(summary = "Get all restaurants", description = "Retrieve a paginated list of all restaurants.")
    @GetMapping("/getAll")
    public ResponseEntity<Page<RestaurantResponse>> getAllRestaurants(
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Entering getAllRestaurants method with pageNumber: {} and pageSize: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RestaurantResponse> allRestaurants = restaurantService.getAllRestaurant(pageable);
        logger.info("Successfully retrieved {} restaurants", allRestaurants.getTotalElements());
        return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
    }

    //    ------------------------------------getRestaurantByEmail--------------------------------------------------
    @Operation(summary = "Get restaurant by email", description = "Retrieve restaurant details by email address.")
    @GetMapping("/email/{email}")
    public ResponseEntity<RestaurantResponse> getRestaurantByEmail(
            @Parameter(description = "Email address of the restaurant") @PathVariable String email) {
        logger.info("Entering getRestaurantByEmail method for email: {}", email);
        RestaurantResponse restaurant = restaurantService.getRestaurantByEmail(email);
        logger.info("Restaurant found by email: {}", restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.FOUND);
    }

    //    ------------------------------------get restaurant by id--------------------------------------------------
    @Operation(summary = "Find restaurant by ID", description = "Retrieve restaurant details by restaurant ID.")
    @GetMapping("/restaurantId/{id}")
    @CircuitBreaker(name = "MenuService",fallbackMethod = "getMenuByRestaurantIdFallback")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @Parameter(description = "ID of the restaurant") @PathVariable Integer id) {
        logger.info("Entering getRestaurantById method for restaurantId: {}", id);
        RestaurantResponse restaurant = restaurantService.FindRestaurantById(id);
        logger.info("Restaurant found by id: {}", restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
//    ------------------------------------------Fallback Method

    //    ------------------------------------get restaurant by name--------------------------------------------------
    @Operation(summary = "Get restaurants by name", description = "Retrieve a paginated list of restaurants by name.")
    @GetMapping("/name/{name}")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantByName(
            @Parameter(description = "Name of the restaurant") @PathVariable String name,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Entering getRestaurantByName method for name: {}", name);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RestaurantResponse> restaurantsByName = restaurantService.getRestaurantByName(name, pageable);
        logger.info("Successfully retrieved {} restaurants by name", restaurantsByName.getTotalElements());
        return new ResponseEntity<>(restaurantsByName, HttpStatus.OK);
    }

    //    ------------------------------------getRestaurantByCity--------------------------------------------------
    @Operation(summary = "Get restaurants by city", description = "Retrieve a paginated list of restaurants by city.")
    @GetMapping("/city/{city}")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantByCity(
            @Parameter(description = "City where the restaurant is located") @PathVariable String city,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Entering getRestaurantByCity method for city: {}", city);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RestaurantResponse> restaurantsByCity = restaurantService.getRestaurantByCity(city, pageable);
        logger.info("Successfully retrieved {} restaurants by city", restaurantsByCity.getTotalElements());
        return new ResponseEntity<>(restaurantsByCity, HttpStatus.OK);
    }

    //    ------------------------------------getRestaurantByRating--------------------------------------------------
    @Operation(summary = "Filter restaurants by rating", description = "Retrieve a paginated list of restaurants based on their rating.")
    @GetMapping("/filterByRating")
    public ResponseEntity<PagedModel<EntityModel<RestaurantResponse>>> getRestaurantByRating(
            @Parameter(description = "Minimum rating for filtering restaurants") @RequestParam Double rating,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize,
            PagedResourcesAssembler<RestaurantResponse> pagedResourcesAssembler) {
        logger.info("Entering getRestaurantByRating method for rating: {}", rating);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RestaurantResponse> restaurantsByRating = restaurantService.getRestaurantsByRating(rating, pageable);
        PagedModel<EntityModel<RestaurantResponse>> pagedModel = pagedResourcesAssembler.toModel(restaurantsByRating);
        logger.info("Successfully retrieved {} restaurants by city", restaurantsByRating.getTotalElements());
        return ResponseEntity.ok(pagedModel);
    }

    //    ------------------------------------getRestaurantsByCityAndRating--------------------------------------------------
    @Operation(summary = "Get restaurants by city and rating",
            description = "Retrieve a list of restaurants filtered by city and rating, with pagination support.")
    @GetMapping("/filterByCityAndRating")
    public ResponseEntity<PagedModel<EntityModel<RestaurantResponse>>> getRestaurantsByCityAndRating(
            @Parameter(description = "City where the restaurant is located") @RequestParam String city,
            @Parameter(description = "Minimum rating of the restaurant") @RequestParam Double rating,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize,
            PagedResourcesAssembler<RestaurantResponse> pagedResourcesAssembler
    ) {
        logger.info("Entering getRestaurantsByCityAndRating method for rating: {} and :{}", rating,city);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("rating").descending());
        Page<RestaurantResponse> restaurantsByCityAndRating = restaurantService.getRestaurantsByCityAndRating(city, rating, pageable);
        PagedModel<EntityModel<RestaurantResponse>> pagedModel = pagedResourcesAssembler.toModel(restaurantsByCityAndRating);
        return ResponseEntity.ok(pagedModel);
    }


    //    ------------------------------------getRestaurantByCuisine--------------------------------------------------
    @Operation(summary = "Get restaurants by cuisine", description = "Retrieve a list of restaurants by a specific cuisine.")
    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantByCuisine(
            @Parameter(description = "Cuisine type for filtering restaurants") @PathVariable String cuisine,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Entering getRestaurantByCuisine method for cuisine: {}", cuisine);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RestaurantResponse> restaurantsByCuisine = restaurantService.getRestaurantByCuisine(cuisine, pageable);
        return new ResponseEntity<>(restaurantsByCuisine, HttpStatus.OK);
    }


    //    ------------------------------------getRestaurantRating--------------------------------------------------
    @Operation(summary = "Get restaurant rating",
            description = "Retrieve the rating of a specific restaurant by its ID.")
    @GetMapping("/rating/restaurantId/{restaurantId}")
    public ResponseEntity<RestaurantReviewResponse> getRestaurantRating(
            @Parameter(description = "ID of the restaurant to fetch the rating") @PathVariable Integer restaurantId
    ) {
        logger.info("Entering getRestaurantRating method for restaurantId: {}", restaurantId);
        RestaurantReviewResponse restaurantReview = restaurantService.findRestaurantReviewById(restaurantId);
        logger.info("Successfully retrieved rating for restaurantId: {}", restaurantId);
        return ResponseEntity.ok(restaurantReview);
    }

        @Operation(summary = "Delete restaurant by email", description = "Delete a restaurant by its email address.")
        @DeleteMapping("/{email}")
        public ResponseEntity<String> deleteRestaurantByEmail(
                @Parameter(description = "Email address of the restaurant") @PathVariable String email) {
            logger.info("Entering deleteRestaurantByEmail method for email: {}", email);
            restaurantService.deleteRestaurantByEmail(email);
            logger.info("Restaurant deleted successfully for email: {}", email);
            return new ResponseEntity<>("Restaurant deleted successfully.", HttpStatus.OK);
        }

//-------------------------------------------Fallbacks Method---------------------------------------------------------------------------------

    public ResponseEntity<String>getMenuByRestaurantIdFallback(Integer id,Throwable throwable){
        logger.error("Fallback triggered for restaurantId {} due to : {}",id,throwable.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Menu Service is temporarily unavailable.Please try again later!!");
    }
    public ResponseEntity<String>deleteMenuByRestaurantIdFallback(Integer restaurantId,Throwable throwable){
        logger.error("Fallback triggered for restaurantId {} due to: {}",restaurantId,throwable.getMessage());
        return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Menu Service is temporarily unavailable.Please try again later!! ");
    }
}
