package com.blog.main.services;

import java.util.List;

import com.blog.main.payloads.CategoryDto;

public interface CategoryService {
//	Create
	public CategoryDto createCategory(CategoryDto categoryDto);
//  Update
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
//	Delete
	public void deleteCategory(Integer categoryId);
//	Get
	
	public CategoryDto getCategory(Integer categoryId);
//	GetAll
	public List<CategoryDto>getCategories();
}
