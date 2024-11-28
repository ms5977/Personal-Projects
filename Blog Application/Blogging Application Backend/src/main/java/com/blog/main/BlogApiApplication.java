package com.blog.main;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.blog.main.config.AppConstant;
import com.blog.main.entites.Role;
import com.blog.main.repositories.RoleRep;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition
(
	info = @Info
	(
			title = "Blogging Api",
			version = "1.0.0",
			description = "A comprehensive API for managing blog posts, users, and comments",
			
			contact = @Contact
			(
					name = "Manish Sharma",
					url = "https://github.com/ms5977?tab=repositories",
					email = "Mani.shiv21@gmail.com"
					
			),
			license = @License
			(
					name = "Terms & Condition Accepted",
					url = "https://github.com/ms5977?tab=repositories"
//					identifier = "Bloggin-Api"
					
			)
	),
	       
//			servers = @Server
//			(
//					url = "http://localhost:8080",
//					description = "Local Server"
//			),
			security = @SecurityRequirement(name = "Blogging Api Security")
)
@SecurityScheme
(
		name = "Blogging Api Security",
		in = SecuritySchemeIn.HEADER,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer",
		bearerFormat = "jwt"
		
)





@SpringBootApplication
public class BlogApiApplication  implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRep roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("mani@123"));
		
		try {
			Role role=new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("USER");
			
			List<Role> roles = List.of(role,role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r->System.out.println(r.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
//	private SecurityScheme createAPIKeyScheme() {
//	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
//	        .bearerFormat("JWT")
//	        .scheme("bearer");
//	}
//	@Bean
//	public OpenAPI openAPI() {
//	    return new OpenAPI().addSecurityItem(new SecurityRequirement().
//	            addList("Bearer Authentication"))
//	        .components(new Components().addSecuritySchemes
//	            ("Bearer Authentication", createAPIKeyScheme()))
//	        .info(new Info().title("My REST API")
//	            .description("Some custom description of API.")
//	            .version("1.0").contact(new Contact().name("Sallo Szrajbman")
//	                .email( "www.baeldung.com").url("salloszraj@gmail.com"))
//	            .license(new License().name("License of API")
//	                .url("API license URL")));
//	}
}
