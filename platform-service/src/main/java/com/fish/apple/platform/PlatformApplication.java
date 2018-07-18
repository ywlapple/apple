package com.fish.apple.platform;

import org.springframework.boot.SpringApplication;

import com.fish.apple.core.web.app.AppleApp;

@AppleApp
public class PlatformApplication {

	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PlatformApplication.class, args);
	}

}
