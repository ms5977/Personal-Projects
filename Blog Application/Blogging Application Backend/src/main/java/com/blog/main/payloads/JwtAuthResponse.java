package com.blog.main.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	private UserDto userDto;
}
