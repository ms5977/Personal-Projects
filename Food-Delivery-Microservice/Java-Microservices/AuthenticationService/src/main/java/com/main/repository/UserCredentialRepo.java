package com.main.repository;

import com.main.entity.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCredentialRepo extends MongoRepository<UserCredential,String> {
    Optional<UserCredential>findByUsername(String username);
    Optional<UserCredential>findByEmail(String email);

}
