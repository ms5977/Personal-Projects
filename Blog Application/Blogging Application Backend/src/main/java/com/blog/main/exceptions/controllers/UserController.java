package com.blog.main.exceptions.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.main.payloads.ApiResponse;
import com.blog.main.payloads.UserDto;
import com.blog.main.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User",description = "User Management APIs")
public class UserController 
{
	@Autowired
	private UserService userService;

	
//	Post: create User
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",content = {@Content(schema =@Schema(implementation = UserDto.class),mediaType = "application/json")}),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode ="400",description = "user not created successfully" )
	})
	@Operation(
				summary = "for creating a new  user",
				description = "Enter the User in Json format",
				tags = {"/","post"}
			)
	@PostMapping("/")
	public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
//	Put: update User
	
	@Operation(summary = "For Updating User By Id",
			   description = "For Update a User Specifying its id",
			   tags = {"/{userId}","put"}
			
			)
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
	{
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
//	Delete: Delete User
	@Operation(summary = "For Delete User By Id",
			   description = "For Delete a User Specifying its id",
			   tags = {"/{userId}","delete"}
			
			)
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?>deleteUser(@PathVariable("userId")Integer uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succesfully", true,LocalDateTime.now(),HttpStatus.OK),HttpStatus.OK);
	}
	
//Get : Get All User
	@Operation(summary = "For Getting all Users",
			   tags = {"/","get"}
			
			)
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>getAllUser()
	{
		
		return ResponseEntity.ok(this.userService.getAllUser());
	}

//	Get: Get Single User
	@Operation(summary = "For getting single user By Id",
			   description = "For getting a singleUser object by Specifying its id",
			   tags = {"/{userId}","get"}
			
			)
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getSingleUser(@PathVariable Integer userId)
	{
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
