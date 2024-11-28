package com.blog.main.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min = 4,message = "Min Size of Category title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message = "min size of category description is 10")
	private String categoryDescription;
}
