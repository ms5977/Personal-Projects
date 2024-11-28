package com.blog.main.exceptions.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.main.payloads.ApiResponse;
import com.blog.main.payloads.CommentDto;
import com.blog.main.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Comment's",description = "Comments Management APIs")
public class CommentController {
	@Autowired
	private CommentService commentService;

	
	@Operation(
			summary = "for creating a new  Comment",
			description = "provide the post id nad comments in Json format",
			tags = {"/post/{postId}/comments","post"}
		)
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.OK);
	}
	@Operation(summary = "For Delete Comment By Id",
			   description = "For Delete a Comment Specifying its id",
			   tags = {"/comments/{commentId}","delete"}
			
			)
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true, LocalDateTime.now(), HttpStatus.OK),HttpStatus.OK);
	}
}
