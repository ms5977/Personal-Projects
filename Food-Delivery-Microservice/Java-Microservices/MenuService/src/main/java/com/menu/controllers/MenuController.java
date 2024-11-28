package com.menu.controllers;

import com.menu.dto.MenuReviewResponse;
import com.menu.services.MenuService;
import com.menu.dto.MenuRequest;
import com.menu.dto.MenuResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/menu")
@Tag(name = "Menu API",description = "Endpoint for menu management.")
public class MenuController {
    @Autowired
    private MenuService menuService;
    private static final Logger logger= LoggerFactory.getLogger(MenuController.class);

//------------------------------------create-menu---------------------------------------------------------
    @Operation(summary = "Create a new menu item",
            description = "Creates a new menu item for a specific restaurant and dish type.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @CircuitBreaker(name = "restaurantBreaker",fallbackMethod = "restaurantFallback")
    @PostMapping("/create-menu/dishType/{dishId}/restaurantId/{restaurantId}")
    public ResponseEntity<MenuResponse> createMenu(@Valid @RequestBody MenuRequest menuRequest, @PathVariable Long dishId, @PathVariable Integer restaurantId) {
        logger.info("Creating new menu item for restaurantId: {} and dishTypeId: {}", restaurantId, dishId);
        MenuResponse menuResponse = menuService.createMenu(menuRequest, dishId, restaurantId);
        logger.info("Menu created successfully: {}", menuResponse);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

   //-------------------------------Falback Method:------------------------------------------------------
   public ResponseEntity<String> restaurantFallback(MenuRequest menuRequest, Long dishId, Integer restaurantId, Throwable throwable) {
       // Log the exception
       logger.error("Fallback triggered due to: {}", throwable.getMessage());
        return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Restaurant Service is unavailable or down");
   }



    //-----------------------------deleteMenu----------------------------------------------------------

    @Operation(summary = "Delete a menu item",
            description = "Deletes a menu item by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Menu not found")
            })
    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        logger.info("Attempting to delete menu with menuId: {}", menuId);
        try {
            menuService.deleteMenu(menuId);
            logger.info("Menu with menuId: {} deleted successfully", menuId);
            return new ResponseEntity<>("Menu Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting menu with menuId: {}", menuId, e);
            return new ResponseEntity<>("Error deleting menu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //    @PutMapping("/update-menu/menuId/{menuId}/restaurantId/{restaurantId}/dishTypeId/{dishTypeId}")
//    public  ResponseEntity<MenuDto>updateMenu(@Valid @PathVariable Long menuId,@RequestBody MenuDto menuDto,@PathVariable Integer restaurantId,@PathVariable Long dishTypeId){
//        MenuDto updateMenu = menuService.updateMenu(menuId, menuDto, restaurantId, dishTypeId);
//        return  new ResponseEntity<>(updateMenu,HttpStatus.OK);
//    }

    //-----------------------------updateMenuOnly----------------------------------------------------------

    @Operation(summary = "Update an existing menu item",
            description = "Updates an existing menu item with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Menu not found")
            })
    @PutMapping("/update-menu/menuId/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long menuId, @Valid @RequestBody MenuRequest menuRequest) {
        logger.info("Updating menu with menuId: {}", menuId);
        MenuResponse updatedMenu = menuService.updateMenuOnly(menuId, menuRequest);
        logger.info("Menu with menuId: {} updated successfully", menuId);
        return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
    }


    //-----------------------------getAllMenu----------------------------------------------------------
    @Operation(summary = "Get all menus",
            description = "Retrieves all menu items with pagination support.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully")
            })
    @GetMapping("/")
    public ResponseEntity<Page<MenuResponse>> getAllMenu(@RequestParam(defaultValue = "0", required = false) Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<MenuResponse> menuResponses = menuService.getAllMenu(pageable);
        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }


    //-----------------------------getMenuById----------------------------------------------------------
    @Operation(summary = "Get menu by ID",
            description = "Retrieves a menu item by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu found"),
                    @ApiResponse(responseCode = "404", description = "Menu not found")
            })
    @GetMapping("/menuId/{menuId}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable Long menuId) {
        logger.info("Fetching menu with menuId: {}", menuId);
        MenuResponse menuById = menuService.getMenuById(menuId);
        logger.info("Fetched menu: {}", menuById);
        return new ResponseEntity<>(menuById, HttpStatus.OK);
    }



    //-----------------------------findMenuByItemName----------------------------------------------------------
    @Operation(summary = "Search menu by item name",
            description = "Search for menu items by their item name with pagination support.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No menus found with the given name")
            })
    @GetMapping("/searchByItemName/{itemName}")
    public ResponseEntity<Page<MenuResponse>> findMenuByItemName(@PathVariable String itemName, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Searching menus by item name: {} with page number: {} and page size: {}", itemName, pageNumber, pageSize);
        Page<MenuResponse> menuByItemName = menuService.getMenuByItemName(itemName, PageRequest.of(pageNumber, pageSize));
        logger.info("Found {} menus matching item name: {}", menuByItemName.getTotalElements(), itemName);
        return new ResponseEntity<>(menuByItemName, HttpStatus.FOUND);
    }




    //-----------------------------findByRatingGreaterThanEqual----------------------------------------------------------
    @Operation(summary = "Search menu by rating",
            description = "Search for menu items with a rating greater than or equal to the specified value.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No menus found with the given rating")
            })
    @GetMapping("/searchByRating")
    public ResponseEntity<Page<MenuResponse>> findByRatingGreaterThanEqual(
            @RequestParam(defaultValue = "5.0", required = false) BigDecimal rating,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        logger.info("Searching menus with rating >= {} with page number: {} and page size: {}", rating, pageNumber, pageSize);
        Page<MenuResponse> menuByRating = menuService.findByRating(rating, PageRequest.of(pageNumber, pageSize));
        logger.info("Found {} menus with rating >= {}", menuByRating.getTotalElements(), rating);
        return new ResponseEntity<>(menuByRating, HttpStatus.FOUND);
    }




    //-----------------------------findAllMenusSortedByPrice----------------------------------------------------------
    @Operation(summary = "Search menu by price",
            description = "Search for menu items sorted by price (ascending or descending).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully")
            })
    @GetMapping("/searchByPrice")
    public ResponseEntity<Page<MenuResponse>> findAllMenusSortedByPrice(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        logger.info("Searching menus sorted by price, sort direction: {} with page number: {} and page size: {}", sortDirection, pageNumber, pageSize);
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("price").ascending() : Sort.by("price").descending();
        Page<MenuResponse> allMenusSortedByPrice = menuService.getAllMenusSortedByPrice(PageRequest.of(pageNumber, pageSize, sort));
        logger.info("Found {} menus sorted by price", allMenusSortedByPrice.getTotalElements());
        return new ResponseEntity<>(allMenusSortedByPrice, HttpStatus.OK);
    }



    //-----------------------------findByItemNameAndRating----------------------------------------------------------
    @Operation(summary = "Search menu by item name and rating",
            description = "Search for menu items by item name and rating with pagination support.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully")
            })
    @GetMapping("/byItemNameAndRating")
    public ResponseEntity<Page<MenuResponse>> findByItemNameAndRating(
            @RequestParam String itemName,
            @RequestParam BigDecimal rating,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        logger.info("Searching menus by item name: {} and rating: {} with page number: {} and page size: {}", itemName, rating, pageNumber, pageSize);
        Page<MenuResponse> menuByItemNameAndRating = menuService.findByItemNameAndRating(itemName, rating, PageRequest.of(pageNumber, pageSize));
        logger.info("Found {} menus matching item name: {} and rating: {}", menuByItemNameAndRating.getTotalElements(), itemName, rating);
        return new ResponseEntity<>(menuByItemNameAndRating, HttpStatus.OK);
    }



    //-----------------------------searchMenus----------------------------------------------------------
    @Operation(summary = "Search menus with various filters",
            description = "Search for menus using a combination of item name, rating, and price filters with pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully")
            })
    @GetMapping("/searchMenus")
    public ResponseEntity<Page<MenuResponse>> searchMenus(
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) BigDecimal rating,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        logger.info("Searching menus with filters itemName: {}, rating: {}, sort direction: {} with page number: {} and page size: {}", itemName, rating, sortDirection, pageNumber, pageSize);
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("price").ascending() : Sort.by("price").descending();
        Page<MenuResponse> menuResult = menuService.searchMenus(itemName, rating, PageRequest.of(pageNumber, pageSize, sort));
        logger.info("Found {} menus with filters itemName: {}, rating: {}", menuResult.getTotalElements(), itemName, rating);
        return new ResponseEntity<>(menuResult, HttpStatus.OK);
    }


        //-----------------------------getMenuByRestaurantId----------------------------------------------------------
    @Operation(summary = "Get menu by restaurant ID",
            description = "Retrieve a list of menus by restaurant ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found")
            })
    @GetMapping("/getByRestaurantId/{restaurantId}")
    public ResponseEntity<List<MenuResponse>> getMenuByRestaurantId(@PathVariable Integer restaurantId) {
        logger.info("Fetching menus for restaurantId: {}", restaurantId);

        try {
            List<MenuResponse> menuResponses = menuService.getMenuByRestaurantId(restaurantId);

            if (menuResponses.isEmpty()) {
                logger.warn("No menus found for restaurantId: {}", restaurantId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no menus found
            }

            logger.info("Menus retrieved successfully for restaurantId: {}", restaurantId);
            return new ResponseEntity<>(menuResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching menu for restaurantId: {} - {}", restaurantId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 if there's an exception
        }
    }


    //-----------------------------deleteMenuByRestaurantId----------------------------------------------------------
    @Operation(description = "Delete Menu by Restaurant ID",
            summary = "Deletes the menu associated with the given restaurant ID. No content is returned on success.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/delete/restaurantId/{restaurantId}")
    public ResponseEntity<List<MenuResponse>> deleteMenuByRestaurantId(@PathVariable Integer restaurantId) {
        logger.info("Attempting to delete menus for restaurantId: {}", restaurantId);

        List<MenuResponse> menuResponses = menuService.deleteMenuByRestaurantId(restaurantId);

        if (menuResponses.isEmpty()) {
            logger.warn("No menus found to delete for restaurantId: {}", restaurantId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no menus are found to delete
        }

        logger.info("Menu deleted successfully for restaurantId: {}", restaurantId);
        return new ResponseEntity<>(menuResponses, HttpStatus.NO_CONTENT);  // Return 204 for successful deletion
    }




    //-----------------------------getMenuReviewByMenuId----------------------------------------------------------
    @Operation(summary = "Get Menu Review by Menu ID",
            description = "Fetches the review for a specific menu by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved menu review"),
            @ApiResponse(responseCode = "404", description = "Menu review not found")
    })
    @GetMapping("/review/menuId/{menuId}")
    public ResponseEntity<MenuReviewResponse> getMenuReviewByMenuId(@PathVariable Long menuId) {
        logger.info("Fetching review for menu with menuId: {}", menuId);
        try {
            MenuReviewResponse reviewByMenuId = menuService.findReviewByMenuId(menuId);
            logger.info("Fetched review for menuId: {}", menuId);
            return new ResponseEntity<>(reviewByMenuId, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Review not found for menuId: {}", menuId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //-----------------------------updateMenuReview----------------------------------------------------------
    @Operation(summary = "Update Menu Review",
            description = "Updates the review rating for a menu item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated menu review"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @PutMapping("/update/review/menuId/{menuId}")
    public ResponseEntity<MenuReviewResponse> updateMenuReview(@PathVariable Long menuId, @RequestParam(required = true) BigDecimal rating) {
        logger.info("Attempting to update review for menu with menuId: {} and rating: {}", menuId, rating);

        MenuReviewResponse menuReview = menuService.updateMenuReviewById(menuId, rating);

        if (menuReview == null) {
            logger.warn("Menu review not found for menuId: {}", menuId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // In case the menuId does not exist
        }

        logger.info("Menu review updated successfully for menuId: {}", menuId);
        return new ResponseEntity<>(menuReview, HttpStatus.OK);
    }











//    @GetMapping("/getByRestaurantId/{restaurantId}")
//    public  ResponseEntity<Page<MenuResponse>>getMenuByRestaurantId(
//            @PathVariable Integer restaurantId,
//            @RequestParam(defaultValue = "0")Integer pageNumber,
//            @RequestParam(defaultValue = "10")Integer pageSize
//    ){
//        Pageable pageable=PageRequest.of(pageNumber,pageSize);
//        Page<MenuResponse> menuResponses = menuService.getMenuByRestaurantId(restaurantId, pageable);
//        return  new ResponseEntity<>(menuResponses,HttpStatus.OK);
//    }


}
