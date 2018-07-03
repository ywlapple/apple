package com.fish.apple.core.common.domain;

import java.io.Serializable;
import java.util.Date;


public interface Domainable extends Serializable {


	String getTenantCode();
	void setTenantCode(String tenantCode );
    Date getCreateTime();
	void setCreateTime(Date createTime) ;
	Date getUpdateTime() ;
	void setUpdateTime(Date updateTime) ;
	String getCreateUser() ;
	void setCreateUser(String createUser) ;
	String getUpdateUser();
	void setUpdateUser(String updateUser) ;
    
}
