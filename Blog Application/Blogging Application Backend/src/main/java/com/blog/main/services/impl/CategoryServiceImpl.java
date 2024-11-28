package com.blog.main.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.main.entites.Category;
import com.blog.main.exceptions.ResourceNotFoundException;
import com.blog.main.payloads.CategoryDto;
import com.blog.main.repositories.CategoryRepo;
import com.blog.main.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	Create
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = categoryRepo.save(category);
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}
//Update
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
											.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCat = this.categoryRepo.save(category);
		return this.modelMapper.map(updateCat, CategoryDto.class);
	}
//Delete
	@Override
	public void deleteCategory(Integer categoryId) {
		 Category category = this.categoryRepo.findById(categoryId)
		 									.orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
		 this.categoryRepo.delete(category);
	}
//Get
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
								.orElseThrow(()->new ResourceNotFoundException("Category", "Category", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}
//Get All
	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
