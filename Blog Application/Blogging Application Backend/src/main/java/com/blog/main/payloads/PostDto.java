package com.blog.main.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.main.entites.Category;
import com.blog.main.entites.Comment;
import com.blog.main.entites.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	@NotEmpty
	@Size(min = 4 ,message = "title Must contain atleast four character")
	private String title;
	
	@NotBlank(message = "Content Cannot Be Empty")
	private String content;
	
	private String imageName;
	
	private Date addDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<CommentDto>();
}
