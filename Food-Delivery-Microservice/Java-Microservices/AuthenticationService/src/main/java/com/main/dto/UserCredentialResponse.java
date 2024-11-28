package com.main.dto;

import com.main.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialResponse {
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<Role>roles;
}
