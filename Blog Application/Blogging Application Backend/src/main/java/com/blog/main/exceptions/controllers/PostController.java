package com.blog.main.exceptions.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.main.config.AppConstant;
import com.blog.main.payloads.ApiResponse;
import com.blog.main.payloads.PostDto;
import com.blog.main.payloads.PostResponse;
import com.blog.main.services.FileService;
import com.blog.main.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Post Service",description = "Post Management APIs")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
//	------------------------------create Post--------------------------------------
	@Operation(
			summary = "for creating a new  post",
			description = "Enter the post in Json format",
			tags = {"/user/{userId}/category/{categoryId}/posts","post"}
		)
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
//	-----------------------Get by User--------------------------
//	Get: Get Single User
	@Operation(summary = "For getting Post by user and its user id",
			   description = "For getting a singleUser Post",
			   tags = {"/user/{userId}/posts","get"}
			
			)
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
//	-------------------------------Get By Category-----------------------
	@Operation(summary = "For getting Post by category and its category id",
			   description = "For getting a singleUser object by Specifying its id",
			   tags = {"/category/{categoryId}/posts","get"}
			
			)
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
//	------------------------Get Post By Id-------------------------
	@Operation(summary = "For  single post By Id",
			   description = "For getting a single post object by Specifying its id",
			   tags = {"/posts/{postId}","get"}
			
			)
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId)
	{
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}

//	--------------------------------Delete PostById--------------------
	@Operation(summary = "For Delete post By Id",
			   description = "For Delete a post Specifying its id",
			   tags = {"/posts/{postId}","delete"}
			
			)
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse>deletePostByID(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Succesfully", true, LocalDateTime.now(), HttpStatus.OK),HttpStatus.OK);
	}
//	----------------------------------Update Post----------------------
	@Operation(summary = "For Updating post By Id",
			   description = "For Update a post Specifying its id",
			   tags = {"/posts/{postId}","put"}
			
			)
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		System.out.println(postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
//-------------------	GetAllPost-----------------------------------
	@Operation(summary = "For Getting all post",
			   tags = {"/post/","get"}
			
			)
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>getAllPost(
			@Parameter(description = "Provide the pageNumber till you want") @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
			@Parameter(description = "Specify the page size") @RequestParam (value = "pageSize",defaultValue=AppConstant.PAGE_SIZE,required=false) Integer pageSize,
			@Parameter(description = "provide the sorting order") @RequestParam(value = "sortBy",defaultValue = AppConstant.SORTBY,required = false)String sortBy,
			@Parameter(description = "provide the Sort Direction") @RequestParam(value = "sortDir",defaultValue = AppConstant.SORTDIR,required = false)String sortDir
			){
	      PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
//	-------------------------------SearchPostByTitle or PostBySearchKeyWord--------------------
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword)
	{
		List<PostDto> postsDtos = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
	}
//	SearchPostByContent
//	PostBySearchKeyWord
	@Operation(summary = "For Searching a post By keyword",
			   description = "For Searching a post Specifying the keyword",
			   tags = {"/posts/content/{keyword}","get"}
			
			)
	@GetMapping("/posts/content/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByContent(@PathVariable("keyword") String keyword)
	{
		List<PostDto> postsDtos = this.postService.searchPostByContentContaining(keyword);
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
	}
	

//                       	upload Image
// ----------------------Post Image Uploded--------------------------
	@Operation(summary = "For Uploading a image By post Id",
			   tags = {"/post/image/upload/{postId}","post"}
			
			)
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto>uploadPostImage(@RequestParam("image")MultipartFile fileimage,@PathVariable Integer postId) throws IOException{
		
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, fileimage);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
	
		return new  ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
//								Serve Image
//----------------------------Method to Serve files-------------------
	@Operation(summary = "For Serve a image By image name",
			   tags = {"/post/image/upload/{postId}","get"}
			
			)
	@GetMapping(value = "/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName
							,HttpServletResponse response) throws IOException {
		
		
		
		InputStream resources = this.fileService.getResources(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resources, response.getOutputStream());
	
	}
}
