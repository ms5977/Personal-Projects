package com.main.security;

import com.main.entity.UserCredential;
import com.main.exception.ResourceNotFoundException;
import com.main.repository.UserCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserCredentialRepo userCredentialRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepo.findByUsername(username)
                .or(()->userCredentialRepo.findByEmail(username))
                .orElseThrow(() -> new ResourceNotFoundException("user not found with username or email :" + username));
        return new CustomUserDetail(userCredential);
    }
}
