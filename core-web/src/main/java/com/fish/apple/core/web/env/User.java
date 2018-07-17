package com.fish.apple.core.web.env;

import lombok.Data;

@Data
public class User {

	private String tenantNo;
	private String personNo;
	private String accountNo;
	private String roleNo ; 
	private String orgNo ;
	private String menuNo;
	
//	private String token;
	
	public final static User systemUser ;
	public final static String sysTenantNo = "syst";
	public final static String systemPersonNo = "sysuser" ;
	public final static String systemAccountNo = "sysact";
	public final static String systemRoleNo = "sysrole";
	public final static String systemOrgNo = "sysOrg";
	public final static String systemMenuNo = "sysMenu";
	static {
		systemUser = new User();
		systemUser.setTenantNo(sysTenantNo);
		systemUser.setPersonNo(systemPersonNo);
		systemUser.setAccountNo(systemAccountNo);
		systemUser.setRoleNo(systemRoleNo);
		systemUser.setOrgNo(systemOrgNo);
		systemUser.setMenuNo(systemMenuNo);
	}
}
