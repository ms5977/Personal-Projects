package com.menu.services;

import com.menu.dto.DishTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DishTypeService {
    DishTypeDto createDishType(DishTypeDto dishTypeDto);
    Page<DishTypeDto> getAllDishType(Pageable pageable);
    Page<DishTypeDto>getDishByDishCategory(String type,Pageable pageable);
    void deleteDishType(Long dishTypeId);
    DishTypeDto updateDishType(Long dishTypeId,DishTypeDto dishTypeDto);
}
