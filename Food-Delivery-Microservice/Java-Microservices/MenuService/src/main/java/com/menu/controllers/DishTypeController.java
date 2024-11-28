package com.menu.controllers;

import com.menu.services.DishTypeService;
import com.menu.dto.DishTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dish")
public class DishTypeController {

    private static final Logger logger = LoggerFactory.getLogger(DishTypeController.class);

    @Autowired
    private DishTypeService dishTypeService;

    @Operation(summary = "Create a new Dish Type", description = "Creates a new dish type for the menu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish type created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    @PostMapping("/")
    public ResponseEntity<DishTypeDto> createDish(@Valid @RequestBody DishTypeDto dishTypeDto) {
        logger.info("Creating new Dish Type: {}", dishTypeDto);
        try {
            DishTypeDto dishType = dishTypeService.createDishType(dishTypeDto);
            logger.info("Dish Type created successfully: {}", dishType);
            return new ResponseEntity<>(dishType, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating dish type: {}", dishTypeDto, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update an existing Dish Type", description = "Updates the details of an existing dish type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish type updated successfully."),
            @ApiResponse(responseCode = "404", description = "Dish type not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<DishTypeDto> updateDishType(
            @Parameter(description = "ID of the dish type to be updated") @PathVariable Long id,
            @RequestBody DishTypeDto dishTypeDto) {
        logger.info("Updating Dish Type with ID {}: {}", id, dishTypeDto);
        try {
            DishTypeDto updatedDishType = dishTypeService.updateDishType(id, dishTypeDto);
            logger.info("Dish Type updated successfully: {}", updatedDishType);
            return new ResponseEntity<>(updatedDishType, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating dish type with ID {}: {}", id, dishTypeDto, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Dish Type", description = "Deletes an existing dish type from the menu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish type deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Dish type not found.")
    })
    @DeleteMapping("/dishTypeId/{id}")
    public ResponseEntity<String> deleteDishType(@Parameter(description = "ID of the dish type to be deleted") @PathVariable Long id) {
        logger.info("Deleting Dish Type with ID: {}", id);
        try {
            dishTypeService.deleteDishType(id);
            logger.info("Dish Type deleted successfully with ID: {}", id);
            return new ResponseEntity<>("Dish type Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting dish type with ID: {}", id, e);
            return new ResponseEntity<>("Dish type not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all Dish Types", description = "Retrieves a paginated list of all dish types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of dish types retrieved successfully.")
    })
    @GetMapping("/")
    public ResponseEntity<Page<DishTypeDto>> getAllDishType(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Retrieving all dish types with pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DishTypeDto> allDishType = dishTypeService.getAllDishType(pageable);
        logger.info("Retrieved {} dish types", allDishType.getTotalElements());
        return new ResponseEntity<>(allDishType, HttpStatus.OK);
    }

    @Operation(summary = "Search Dish Types by Category", description = "Search for dish types by their category and return a paginated list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of dish types filtered by category retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid category or page size input.")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<DishTypeDto>> getDishTypesByType(
            @RequestParam String dishCategory,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.info("Searching for dish types in category: {} with pageNumber: {}, pageSize: {}", dishCategory, pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<DishTypeDto> dishTypesByType = dishTypeService.getDishByDishCategory(dishCategory, pageable);
            logger.info("Retrieved {} dish types for category: {}", dishTypesByType.getTotalElements(), dishCategory);
            return new ResponseEntity<>(dishTypesByType, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error searching dish types for category: {}", dishCategory, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
