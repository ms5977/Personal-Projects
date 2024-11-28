package com.blog.main.exceptions.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.main.payloads.ApiResponse;
import com.blog.main.payloads.CategoryDto;
import com.blog.main.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
@Tag(name = "Category",description = "Categories Management APIs")
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
//	create
	@Operation(
			summary = "for creating a new  Category",
			description = "provide the Category data  in Json format",
			tags = {"/","post"}
		)
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
//-----------------------	Update---------------------------------
	@Operation(summary = "For Updating Category By Id",
			   description = "For Update a Category Specifying its id",
			   tags = {"Category","put"}
			
			)
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
	{
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
//	-----------------------Delete------------------------------
	@Operation(summary = "For Delete Category By Id",
			   description = "For Delete a Category Specifying its id",
			   tags = {"Category","delete"}
			
			)
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer catId)
	{
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted Successfully",true, LocalDateTime.now(), HttpStatus.OK),HttpStatus.OK);
	}
//	---------------------------------Get Single---------------------------------
	
	@Operation(summary = "For getting single Category By Id",
			   description = "For getting a single Category object by Specifying its id",
			   tags = {"User","get"}
			
			)
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer catId)
	{
//		System.out.println(catId);
		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		return new  ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
//	------------------------GetAll---------------------------
	@Operation(summary = "For Getting all Category",
			   tags = {"/","get"}
			
			)
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getCategories()
	{
		List<CategoryDto> catgoriesDtos = this.categoryService.getCategories();
		for (CategoryDto categoryDto : catgoriesDtos) 
		{
			System.out.println(categoryDto.getCategoryTitle());
		}
//		System.out.println(catgoriesDtos);
		return ResponseEntity.ok(catgoriesDtos);
	}
	
}
