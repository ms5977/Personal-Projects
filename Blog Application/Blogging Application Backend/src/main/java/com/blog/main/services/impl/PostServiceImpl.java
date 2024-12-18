package com.blog.main.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.main.entites.Category;
import com.blog.main.entites.Post;
import com.blog.main.entites.User;
import com.blog.main.exceptions.ResourceNotFoundException;
import com.blog.main.payloads.PostDto;
import com.blog.main.payloads.PostResponse;
import com.blog.main.repositories.CategoryRepo;
import com.blog.main.repositories.PostRepo;
import com.blog.main.repositories.UserRepo;
import com.blog.main.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.mapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		 
	    // Check if the category exists
	    Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId())
	            .orElseThrow(() -> 
	            new ResourceNotFoundException("Category", "Category Id", postDto.getCategory().getCategoryId()));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setCategory(category);
		Post updatedPost = this.postRepo.save(post);
		return this.mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).toList();
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).toList();
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).toList();
		return postDtos;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
//		Sort sort=null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		}
//		else {
//		   sort = Sort.by(sortBy).descending();
//		}
	
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable =PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->mapper.map(post, PostDto.class)).toList();
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElemnts(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> searchPostByContentContaining(String keyword) {
		List<Post> posts = this.postRepo.findByContentContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).toList();
		
		return postDtos;
	}

}
