package com.blog.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.main.entites.Category;
import com.blog.main.entites.Post;
import com.blog.main.entites.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	List<Post>findByUser(User user);
	List<Post>findByCategory(Category category);
	List<Post> findByTitleContaining(String keyword);
	List<Post> findByContentContaining(String content);
}
