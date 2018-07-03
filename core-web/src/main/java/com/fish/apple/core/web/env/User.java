package com.fish.apple.core.web.env;

import lombok.Data;

@Data
public class User {

	private String tenantCode;
	private String userNo;
	
//	private String token;
	
	public final static User systemUser ;
	static {
		systemUser = new User();
		systemUser.setTenantCode("sys");
		systemUser.setUserNo("sys");
	}
}
