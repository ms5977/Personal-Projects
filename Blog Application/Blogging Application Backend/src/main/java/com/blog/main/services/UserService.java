package com.blog.main.services;

import java.util.List;

import com.blog.main.payloads.UserDto;

public interface UserService
{
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);
	UserDto registerNewUser(UserDto user);
}
