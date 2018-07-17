package com.fish.apple.platform.vo;

import lombok.Data;

@Data
public class Authen {

	private String url;
	private String token;
	private String menuSign ;
	private boolean tokenFresh;
	
}
