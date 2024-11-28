package com.blog.main.services;

import com.blog.main.payloads.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	public void deleteComment(Integer commentId);
}
