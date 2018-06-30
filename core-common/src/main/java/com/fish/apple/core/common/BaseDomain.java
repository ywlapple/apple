package com.fish.apple.core.common;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
public abstract class BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	
    protected String tenantCode;
    
    @JsonIgnore
    protected Date createTime;
    @JsonIgnore
    protected Date updateTime;
    @JsonIgnore
    protected String createUser;
    @JsonIgnore
    protected String updateUser;
    
    /**
     * copy a new domain ,but clear the key or some info like create time .
     * it is business level
     * @param target
     */
    public void copy(BaseDomain target) {
    	target.setTenantCode( this.getTenantCode() );
    }
}
