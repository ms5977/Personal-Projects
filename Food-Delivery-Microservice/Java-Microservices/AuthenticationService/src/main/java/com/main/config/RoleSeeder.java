package com.main.config;

import com.main.entity.Role;
import com.main.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepo roleRepo;

    public RoleSeeder(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.count()==0){
            Role userRole=new Role();
            userRole.setName("USER_ROLE");

            Role adminRole=new Role();
            adminRole.setName("ADMIN_ROLE");

            roleRepo.save(userRole);
            roleRepo.save(adminRole);
        }
    }
}
