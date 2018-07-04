package com.fish.apple.user;

import org.springframework.boot.SpringApplication;

import com.fish.apple.core.web.app.AppleApp;

@AppleApp
public class UserApplication {

	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(UserApplication.class, args);
	}

}
