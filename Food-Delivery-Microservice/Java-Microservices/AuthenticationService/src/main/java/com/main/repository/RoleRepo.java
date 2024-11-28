package com.main.repository;

import com.main.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepo extends MongoRepository<Role,String> {
    Set<Role>findByNameIn(Set<String> name);
}
