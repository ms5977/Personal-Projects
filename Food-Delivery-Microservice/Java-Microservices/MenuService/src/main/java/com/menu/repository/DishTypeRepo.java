package com.menu.repository;

import com.menu.entity.DishType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishTypeRepo extends JpaRepository<DishType,Long> {
    Page<DishType> findByDishCategoryContaining(String dishCategory, Pageable pageable);
}
