package com.fish.apple.core.web.env;

import lombok.Data;

@Data
public class WebEnv {

	private String path;
	private String contentPath;
	private String httpMethod;
	
	private String classUrl;
	private String methodUrl;
	private String controller;
	private String method;
}
