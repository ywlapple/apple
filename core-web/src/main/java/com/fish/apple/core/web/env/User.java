package com.fish.apple.core.web.env;

import lombok.Data;

@Data
public class User {

	private String tenantNo;
	private String personNo;
	private String accountNo;
	
//	private String token;
	
	public final static User systemUser ;
	public final static String sysTenantNo = "syst";
	public final static String systemPersonNo = "sysuser" ;
	public final static String systemAccountNo = "sysact";
	static {
		systemUser = new User();
		systemUser.setTenantNo(sysTenantNo);
		systemUser.setPersonNo(systemPersonNo);
		systemUser.setAccountNo(systemAccountNo);
	}
}
