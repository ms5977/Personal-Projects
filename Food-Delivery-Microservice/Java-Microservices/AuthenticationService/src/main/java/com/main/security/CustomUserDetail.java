package com.main.security;

import com.main.entity.UserCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private final UserCredential userCredential;

    public CustomUserDetail(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return  userCredential.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return userCredential.getPassword();
    }

    @Override
    public String getUsername() {
        if (userCredential.getUsername() != null) {
            return userCredential.getUsername();
        } else {
            return userCredential.getEmail();
        }
    }

}
