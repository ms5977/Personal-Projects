package com.menu.services.implementation;

import com.menu.services.DishTypeService;
import com.menu.dto.DishTypeDto;
import com.menu.entity.DishType;
import com.menu.exception.ResourceNotFoundException;
import com.menu.repository.DishTypeRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DishTypeServiceImpl implements DishTypeService {

    private static final Logger logger = LoggerFactory.getLogger(DishTypeServiceImpl.class);

    @Autowired
    private DishTypeRepo dishTypeRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DishTypeDto createDishType(DishTypeDto dishTypeDto) {
        logger.info("Creating a new DishType with category: {}", dishTypeDto.getDishCategory());

        // Convert DTO to entity and save
        DishType dishType = modelMapper.map(dishTypeDto, DishType.class);
        DishType savedDishType = dishTypeRepo.save(dishType);

        logger.info("DishType created with ID: {}", savedDishType);
        return modelMapper.map(savedDishType, DishTypeDto.class);
    }

    @Override
    public Page<DishTypeDto> getAllDishType(Pageable pageable) {
        logger.info("Fetching all DishTypes with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<DishType> dishTypes = dishTypeRepo.findAll(pageable);
        logger.info("Found {} DishTypes", dishTypes.getTotalElements());
        return dishTypes.map(dishType -> modelMapper.map(dishType, DishTypeDto.class));
    }

    @Override
    public Page<DishTypeDto> getDishByDishCategory(String dishCategory, Pageable pageable) {
        logger.info("Searching DishTypes by category: {}", dishCategory);

        Page<DishType> dishTypes = dishTypeRepo.findByDishCategoryContaining(dishCategory, pageable);
        logger.info("Found {} DishTypes for category {}", dishTypes.getTotalElements(), dishCategory);
        return dishTypes.map(dishType -> modelMapper.map(dishType, DishTypeDto.class));
    }

    @Override
    public void deleteDishType(Long dishTypeId) {
        logger.info("Deleting DishType with ID: {}", dishTypeId);

        DishType dishType = dishTypeRepo.findById(dishTypeId)
                .orElseThrow(() -> {
                    logger.error("DishType with ID {} not found", dishTypeId);
                    return new ResourceNotFoundException("Dish Type not found with id: " + dishTypeId);
                });

        dishTypeRepo.delete(dishType);
        logger.info("DishType with ID: {} deleted successfully", dishTypeId);
    }

    @Override
    public DishTypeDto updateDishType(Long dishTypeId, DishTypeDto dishTypeDto) {
        logger.info("Updating DishType with ID: {}", dishTypeId);

        DishType dishType = dishTypeRepo.findById(dishTypeId)
                .orElseThrow(() -> {
                    logger.error("DishType with ID {} not found", dishTypeId);
                    return new ResourceNotFoundException("Dish Type not found with id: " + dishTypeId);
                });

        // Update the entity fields
        dishType.setDishCategory(dishTypeDto.getDishCategory());
        dishType.setDishDescription(dishTypeDto.getDishDescription());

        DishType updatedDishType = dishTypeRepo.save(dishType);
        logger.info("DishType with ID: {} updated successfully", dishTypeId);

        return modelMapper.map(updatedDishType, DishTypeDto.class);
    }
}
