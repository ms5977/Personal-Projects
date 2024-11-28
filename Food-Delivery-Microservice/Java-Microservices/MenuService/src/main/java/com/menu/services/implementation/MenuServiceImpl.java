package com.menu.services.implementation;

import com.menu.dto.*;
import com.menu.services.MenuService;
import com.menu.client.RestaurantService;
import com.menu.entity.DishType;
import com.menu.entity.Menu;
import com.menu.exception.ResourceNotFoundException;
import com.menu.repository.DishTypeRepo;
import com.menu.repository.MenuRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuRepo menuRepo;
    private final ModelMapper modelMapper;
    private final DishTypeRepo dishTypeRepo;
    private final RestTemplate restTemplate;
    private final RestaurantService restaurantService;

    public MenuServiceImpl(MenuRepo menuRepo, ModelMapper modelMapper, DishTypeRepo dishTypeRepo, RestTemplate restTemplate, RestaurantService restaurantService) {
        this.menuRepo = menuRepo;
        this.modelMapper = modelMapper;
        this.dishTypeRepo = dishTypeRepo;
        this.restTemplate = restTemplate;
        this.restaurantService = restaurantService;
    }

    private Page<MenuResponse> getMenuResponses(Page<Menu> menus) {
        return menus.map(menu -> modelMapper.map(menu, MenuResponse.class));
    }

    @Override
    public MenuResponse createMenu(MenuRequest menuRequest, Long dishTypeId, Integer restaurantId) {
        logger.info("Creating menu for restaurantId: {} with dishTypeId: {}", restaurantId, dishTypeId);

        RestaurantResponse restaurantResponse = restaurantService.getRestaurantById(restaurantId);
        DishType dishType = dishTypeRepo.findById(dishTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DishType not found with id: " + dishTypeId));

        Menu menu = modelMapper.map(menuRequest, Menu.class);
        menu.setImage("menu.png");
        menu.setRating(BigDecimal.valueOf(0.0));
        menu.setDishType(dishType);
        menu.setRestaurantId(restaurantId);

        Menu savedMenu = menuRepo.save(menu);
        MenuResponse menuResponse = modelMapper.map(savedMenu, MenuResponse.class);
        menuResponse.setRestaurantId(restaurantId);

        logger.info("Menu created successfully for restaurantId: {}", restaurantId);
        return menuResponse;
    }

    @Override
    public Page<MenuResponse> getAllMenu(Pageable pageable) {
        logger.info("Fetching all menus with pagination: {}", pageable);

        Page<Menu> menus = menuRepo.findAll(pageable);
        return getMenuResponses(menus);
    }

    @Override
    public void deleteMenu(Long menuId) {
        logger.info("Attempting to delete menu with menuId: {}", menuId);

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + menuId));

        menuRepo.delete(menu);
        logger.info("Menu deleted successfully with menuId: {}", menuId);
    }

    @Override
    public MenuResponse updateMenuOnly(Long menuId, MenuRequest menuRequest) {
        logger.info("Updating menu with menuId: {}", menuId);

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + menuId));

        menu.setItemName(menuRequest.getItemName());
        menu.setPrice(menuRequest.getPrice());
        menu.setItemDescription(menuRequest.getItemDescription());
        menu.setAvailable(menuRequest.getAvailable());
        if (menuRequest.getImage() != null) {
            menu.setImage(menuRequest.getImage());
        }

        Menu updatedMenu = menuRepo.save(menu);
        MenuResponse menuResponse = modelMapper.map(updatedMenu, MenuResponse.class);

        logger.info("Menu updated successfully with menuId: {}", menuId);
        return menuResponse;
    }

    @Override
    public Page<MenuResponse> getMenuByItemName(String itemName, Pageable pageable) {
        logger.info("Fetching menus with item name: {} and pagination: {}", itemName, pageable);

        Page<Menu> menus = menuRepo.findByItemNameIgnoreCaseContaining(itemName, pageable);
        return getMenuResponses(menus);
    }

    @Override
    public Page<MenuResponse> findByRating(BigDecimal rating, Pageable pageable) {
        logger.info("Fetching menus with rating greater than or equal to: {} and pagination: {}", rating, pageable);

        Page<Menu> menus = menuRepo.findByRatingGreaterThanEqual(rating, pageable);
        return getMenuResponses(menus);
    }

    @Override
    public Page<MenuResponse> getAllMenusSortedByPrice(Pageable pageable) {
        logger.info("Fetching all menus sorted by price with pagination: {}", pageable);

        Page<Menu> menus = menuRepo.findAll(pageable);
        return getMenuResponses(menus);
    }

    @Override
    public Page<MenuResponse> findByItemNameAndRating(String itemName, BigDecimal rating, Pageable pageable) {
        logger.info("Fetching menus with item name: {} and rating: {} and pagination: {}", itemName, rating, pageable);

        Page<Menu> menus = menuRepo.findByItemNameIgnoreCaseContainingAndRatingGreaterThanEqual(itemName, rating, pageable);
        return getMenuResponses(menus);
    }

    @Override
    public Page<MenuResponse> searchMenus(String itemName, BigDecimal rating, Pageable pageable) {
        logger.info("Searching menus with item name: {} and rating: {} and pagination: {}", itemName, rating, pageable);

        if (itemName != null && rating != null) {
            Page<Menu> menus = menuRepo.findByItemNameIgnoreCaseContainingAndRatingGreaterThanEqual(itemName, rating, pageable);
            return getMenuResponses(menus);
        } else if (itemName != null) {
            Page<Menu> menus = menuRepo.findByItemNameIgnoreCaseContaining(itemName, pageable);
            return getMenuResponses(menus);
        } else if (rating != null) {
            Page<Menu> menus = menuRepo.findByRatingGreaterThanEqual(rating, pageable);
            return getMenuResponses(menus);
        } else {
            Page<Menu> menus = menuRepo.findAll(pageable);
            return getMenuResponses(menus);
        }
    }

    @Override
    public MenuResponse getMenuById(Long menuId) {
        logger.info("Fetching menu by id: {}", menuId);

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + menuId));

        MenuResponse menuResponse = modelMapper.map(menu, MenuResponse.class);
        logger.info("Menu fetched successfully with menuId: {}", menuId);
        return menuResponse;
    }

    @Override
    public List<MenuResponse> getMenuByRestaurantId(Integer restaurantId) {
        logger.info("Fetching menus for restaurantId: {}", restaurantId);

        long startTime = System.currentTimeMillis();
        List<Menu> menus = menuRepo.findByRestaurantId(restaurantId);
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Query Duration: {} ms", duration);

        return menus.stream().map(menu -> modelMapper.map(menu, MenuResponse.class)).toList();
    }

    @Override
    @Transactional
    public List<MenuResponse> deleteMenuByRestaurantId(Integer restaurantId) {
        logger.info("Deleting menus for restaurantId: {}", restaurantId);

        List<Menu> menus = menuRepo.findByRestaurantId(restaurantId);
        List<MenuResponse> menuResponses = menus.stream().map(menu -> modelMapper.map(menu, MenuResponse.class)).toList();

        int deletedCount = menuRepo.deleteByRestaurantId(restaurantId);
        if (deletedCount == 0) {
            logger.warn("No menus found to delete for restaurantId: {}", restaurantId);
            throw new ResourceNotFoundException("No menus found for the given restaurant ID: " + restaurantId);
        }

        logger.info("Successfully deleted {} menus for restaurantId: {}", deletedCount, restaurantId);
        return menuResponses;
    }

    @Override
    public MenuReviewResponse findReviewByMenuId(Long menuId) {
        logger.info("Fetching review for menuId: {}", menuId);

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("No menus found for the given menu id: " + menuId));

        return modelMapper.map(menu, MenuReviewResponse.class);
    }

    @Override
    public MenuReviewResponse updateMenuReviewById(Long menuId, BigDecimal rating) {
        logger.info("Updating review for menuId: {} with rating: {}", menuId, rating);

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("No menus found for the given menu id: " + menuId));

        menu.setRating(rating);
        Menu savedMenu = menuRepo.save(menu);

        logger.info("Review updated successfully for menuId: {}", menuId);
        return modelMapper.map(savedMenu, MenuReviewResponse.class);
    }
}
