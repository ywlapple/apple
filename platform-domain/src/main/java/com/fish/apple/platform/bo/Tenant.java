package com.fish.apple.platform.bo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fish.apple.core.common.domain.BIdable;
import com.fish.apple.core.web.repository.BIdListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(BIdListener.class)
@Table(name="t_platfom_tenant")
public class Tenant implements BIdable  {

	private static final long serialVersionUID = 9199101828681864740L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String tenantNo;
    
	private String tenantName ;
	
	private String desc;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private String createUser;
    @JsonIgnore
    private String updateUser;
	@Override
	public String getBIdName() {
		return "TenantNo";
	}
	@Override
	public String getBId() {
		
		return this.getTenantNo();
	}
	@Override
	public void setBId(String bId) {
		this.setTenantNo(bId);
	}
}
