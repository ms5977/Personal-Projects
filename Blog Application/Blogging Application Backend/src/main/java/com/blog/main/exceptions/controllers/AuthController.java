package com.blog.main.exceptions.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.main.Security.JwtTokenHelper;
import com.blog.main.entites.User;
import com.blog.main.exceptions.ApiException;
import com.blog.main.payloads.JwtAuthRequest;
import com.blog.main.payloads.JwtAuthResponse;
import com.blog.main.payloads.UserDto;
import com.blog.main.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authroization",description = "Authorization APIs")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Operation(
			summary = "Provide Login Details for genrate the token ",
			description = "provide the login data  in Json format",
			tags = {"/login","post"}
		)
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse>createToken(@RequestBody JwtAuthRequest request)
	{
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse authResponse=new JwtAuthResponse();
		authResponse.setToken(token);
		authResponse.setUserDto(this.mapper.map((User)userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
	}
	
	private void authenticate(String username,String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			throw new ApiException("Invalid username and Password");
		}
	}
	@Operation(
			summary = "Provide register a new user ",
			description = "provide the user data  in Json format",
			tags = {"/register","post"}
		)
	@PostMapping("/register")
	public ResponseEntity<UserDto>registerUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto registerUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
}
