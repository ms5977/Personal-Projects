package com.main.restaurant.services.implementation;
import com.main.restaurant.dto.*;
import com.main.restaurant.client.MenuService;
import com.main.restaurant.services.RestaurantService;
import com.main.restaurant.entity.Restaurant;
import com.main.restaurant.entity.ResturantAddress;
import com.main.restaurant.exception.DuplicateResourceException;
import com.main.restaurant.exception.ResourceNotFoundException;
import com.main.restaurant.repository.RestaurantRepo;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MenuService menuService;
    private static  final Logger logger=LoggerFactory.getLogger(RestaurantServiceImpl.class);
    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {
        logger.info("Creating a new restaurant with name: {}", restaurantRequest.getName());
        if (restaurantRepo.existsByPhoneNumber(restaurantRequest.getPhoneNumber())) {
            logger.error("Restaurant with phone number {} already exists", restaurantRequest.getPhoneNumber());
            throw new DuplicateResourceException("Restaurant with phone number " + restaurantRequest.getPhoneNumber() + " already exists");
        }

        Restaurant restaurant = modelMapper.map(restaurantRequest, Restaurant.class);
        restaurant.setImage("default.png");
        restaurant.setRating(BigDecimal.valueOf(0.0));
        Restaurant savedRestaurant = restaurantRepo.save(restaurant);
        logger.info("Restaurant created successfully with ID: {}", savedRestaurant.getRestaurantId());
        return modelMapper.map(savedRestaurant, RestaurantResponse.class);
    }
    @Override
    public RestaurantResponse updateRestaurantDetails(Integer restaurantId, RestaurantRequest restaurantRequest) {
        logger.info("Updating restaurant with ID: {}", restaurantId);
        ResturantAddress resturantAddress = modelMapper.map(restaurantRequest.getAddress(), ResturantAddress.class);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> {
                    logger.error("Restaurant with ID {} not found", restaurantId);
                    return new ResourceNotFoundException("No restaurant found with ID: " + restaurantId);
                });

        restaurant.setName(restaurantRequest.getName());
        restaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
        restaurant.setAddress(resturantAddress);
        if (restaurantRequest.getImage() != null) {
            restaurant.setImage(restaurantRequest.getImage());
        }

        Restaurant updatedRestaurant = restaurantRepo.save(restaurant);
        logger.info("Restaurant updated successfully with ID: {}", updatedRestaurant.getRestaurantId());
        return modelMapper.map(updatedRestaurant, RestaurantResponse.class);
    }

    @Override
    public void deleteRestaurantByEmail(String email) {
        logger.info("Deleting restaurant with email: {}", email);
        Restaurant restaurant = restaurantRepo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Restaurant with email {} not found", email);
                    return new ResourceNotFoundException("Restaurant not found with email: " + email);
                });

        restaurantRepo.delete(restaurant);
        logger.info("Restaurant with email {} deleted successfully", email);
    }

    @Override
    public RestaurantResponse getRestaurantByEmail(String email) {
        logger.info("Fetching restaurant by email: {}", email);
        Restaurant restaurant = restaurantRepo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Restaurant with email {} not found", email);
                    return new ResourceNotFoundException("No restaurant found with email: " + email);
                });

        logger.info("Restaurant fetched successfully with email: {}", email);
        return modelMapper.map(restaurant, RestaurantResponse.class);
    }

    @Override
    public RestaurantResponse FindRestaurantById(Integer id) {
        logger.info("Fetching restaurant by ID: {}", id);
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Restaurant with ID {} not found", id);
                    return new ResourceNotFoundException("No restaurant found with ID: " + id);
                });

        List<MenuResponse> menuResponses = menuService.getMenuByRestaurantId(restaurant.getRestaurantId());
        logger.info("Fetched {} menus for restaurant ID: {}", menuResponses.size(), id);
        RestaurantResponse response = modelMapper.map(restaurant, RestaurantResponse.class);
        response.setMenuResponse(menuResponses);
        return response;
    }

    @Override
    public Page<RestaurantResponse> getAllRestaurant(Pageable pageable) {
        logger.info("Fetching all restaurants with pagination");
        Page<Restaurant> restaurantPage = restaurantRepo.findAll(pageable);
        return restaurantPage.map(restaurant -> {
            logger.debug("Mapping restaurant with ID: {}", restaurant.getRestaurantId());
            return modelMapper.map(restaurant, RestaurantResponse.class);
        });
    }
    @Override
    public Page<RestaurantResponse> getRestaurantByName(String name, Pageable pageable) {
        logger.info("Fetching restaurants by name: {}", name);
        Page<Restaurant> restaurantPage = restaurantRepo.findByNameContainingIgnoreCase(name, pageable);
        if (restaurantPage.isEmpty()) {
            logger.error("No restaurants found with name: {}", name);
            throw new ResourceNotFoundException("No restaurants found with name: " + name);
        }

        logger.info("Fetched {} restaurants with name: {}", restaurantPage.getTotalElements(), name);
        return restaurantPage.map(restaurant -> modelMapper.map(restaurant, RestaurantResponse.class));
    }

    @Override
    public Page<RestaurantResponse> getRestaurantByCity(String city, Pageable pageable) {
        logger.info("Fetching restaurants by city: {}", city);
        Page<Restaurant> restaurantPage = restaurantRepo.findByAddress_CityContainingIgnoreCase(city, pageable);
        if (restaurantPage.isEmpty()) {
            logger.error("No restaurants found in city: {}", city);
            throw new ResourceNotFoundException("No restaurants found in city: " + city);
        }

        logger.info("Fetched {} restaurants in city: {}", restaurantPage.getTotalElements(), city);
        return restaurantPage.map(restaurant -> modelMapper.map(restaurant, RestaurantResponse.class));
    }


    @Override
    public Page<RestaurantResponse> getRestaurantsByRating(Double rating, Pageable pageable) {
        logger.info("Fetching restaurants with rating >= {}", rating);
        Page<Restaurant> restaurantPage = restaurantRepo.findByRatingGreaterThanEqual(rating, pageable);
        logger.info("Fetched {} restaurants with rating >= {}", restaurantPage.getTotalElements(), rating);
        return restaurantPage.map(restaurant -> modelMapper.map(restaurant, RestaurantResponse.class));
    }

    @Override
    public Page<RestaurantResponse> getRestaurantsByCityAndRating(String city, Double rating, Pageable pageable) {
        logger.info("Fetching restaurants in city: {} with rating >= {}", city, rating);
        Page<Restaurant> restaurantsPage = restaurantRepo.findByAddress_CityContainingIgnoreCaseAndRatingGreaterThanEqual(city, rating, pageable);
        logger.info("Restaurants fetched successfully in city: {} with rating >= {}", city, rating);
        return restaurantsPage.map(restaurant -> modelMapper.map(restaurant, RestaurantResponse.class));
    }


    @Override
    public Page<RestaurantResponse> getRestaurantByCuisine(String cuisine, Pageable pageable) {
        logger.info("Fetching restaurants with cuisine type: {}", cuisine);
        Page<Restaurant> restaurantsPage = restaurantRepo.findByCuisineTypesIgnoreCaseContaining(cuisine, pageable);
        logger.info("Restaurants fetched successfully with cuisine type: {}", cuisine);
        return restaurantsPage.map(restaurant -> modelMapper.map(restaurant, RestaurantResponse.class));
    }
    @Override
    public ResponseEntity<String> deleteRestaurantById(Integer restaurantId) {
        logger.info("Deleting restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> {
                    logger.error("Restaurant not found with ID: {}", restaurantId);
                    return new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
                });
        restaurantRepo.delete(restaurant);
        String responseMessage;
        try {
            List<MenuResponse> menuResponses = menuService.deleteMenuByRestaurantId(restaurant.getRestaurantId());
            if (menuResponses != null && !menuResponses.isEmpty()) {
                responseMessage = "Restaurant and associated menus also deleted";
            } else {
                responseMessage = "Restaurant deleted and no associated menus found";
            }
        } catch (FeignException.NotFound ex) {
            responseMessage = "Restaurant deleted and no associated menus were found.";
        }
        logger.info("Restaurant deleted successfully with ID: {}", restaurantId);
        return ResponseEntity.ok(responseMessage);
    }

    @Override
    public RestaurantReviewResponse updateRestaurantReviewById(Integer restaurantId, BigDecimal rating) {
        logger.info("Updating restaurant review for ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> {
                    logger.error("Restaurant not found with ID: {}", restaurantId);
                    return new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
                });
        restaurant.setRating(rating);
        Restaurant updatedRestaurant = restaurantRepo.save(restaurant);
        logger.info("Restaurant review updated successfully for ID: {}", restaurantId);
        return modelMapper.map(updatedRestaurant, RestaurantReviewResponse.class);
    }

    @Override
    public RestaurantReviewResponse findRestaurantReviewById(Integer restaurantId) {
        logger.info("Fetching restaurant review for ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> {
                    logger.error("Restaurant not found with ID: {}", restaurantId);
                    return new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
                });
        logger.info("Restaurant review fetched successfully for ID: {}", restaurantId);
        return modelMapper.map(restaurant, RestaurantReviewResponse.class);
    }
}


