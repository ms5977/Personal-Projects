package com.blog.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.main.entites.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
