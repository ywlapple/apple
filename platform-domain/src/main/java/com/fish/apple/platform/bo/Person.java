package com.fish.apple.platform.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fish.apple.core.common.dict.Sex;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.BIdable;
import com.fish.apple.core.web.repository.BIdListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(BIdListener.class)
@Table(name="t_platform_person")
public class Person implements BIdable ,Serializable  {
	private static final long serialVersionUID = -1202890621885869400L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@BId
	private String personNo;
	private String name;
	private Sex sex;
	private Date birthDay;
	private String telephone;
	private String email;
	
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private String createUser;
    @JsonIgnore
    private String updateUser;
	
	@Transient
	private List<Org> orgs;
	@Transient
	private List<Role> roles;
	@Override
	public String getBIdName() {
		return "PersonNo";
	}
	@Override
	public String getBId() {
		return this.getPersonNo();
	}
	@Override
	public void setBId(String bId) {
		this.setPersonNo(bId);
		
	}
}
