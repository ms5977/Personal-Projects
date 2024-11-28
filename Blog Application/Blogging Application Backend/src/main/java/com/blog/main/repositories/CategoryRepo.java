package com.blog.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.main.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
