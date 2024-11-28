package com.blog.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.main.entites.Role;

public interface RoleRep extends JpaRepository<Role, Integer> {

}
