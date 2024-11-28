package com.blog.main.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.main.entites.Comment;
import com.blog.main.entites.Post;
import com.blog.main.exceptions.ResourceNotFoundException;
import com.blog.main.payloads.CommentDto;
import com.blog.main.repositories.CommentRepo;
import com.blog.main.repositories.PostRepo;
import com.blog.main.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "Post id", postId));
		System.out.println(post);
		Comment  comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
		commentRepo.delete(comment);
	}

}
