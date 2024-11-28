package com.blog.main.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.blog.main.payloads.PostDto;
import com.blog.main.payloads.PostResponse;

public interface PostService {
//	create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
//	update
	PostDto updatePost(PostDto postDto,Integer postId);
	
//	Delete
	void deletePost(Integer postId);
	
//	Get Single Post
	PostDto getPostById(Integer postId);

//	getAll Post by Category
	List<PostDto>getPostByCategory(Integer categoryId);

//	get all post by user
	List<PostDto>getPostsByUser(Integer userId);
	
//	search Post
	List<PostDto>searchPosts(String keyword);
	List<PostDto>searchPostByContentContaining(String keyword);
	
//	Get ALL Post
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
}
