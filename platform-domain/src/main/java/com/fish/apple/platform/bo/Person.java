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

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fish.apple.core.common.dict.Sex;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.web.repository.BIdListener;

import lombok.Data;

@Data
@Entity
@EntityListeners({AuditingEntityListener.class,BIdListener.class})
@Table(name="t_platform_person")
public class Person implements Serializable  {
	private static final long serialVersionUID = -1202890621885869400L;

	@BId
	private String personNo;
	private String name;
	private Sex sex;
	private Date birthDay;
	private String telephone;
	private String email;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @JsonIgnore
    @CreatedDate
    private Date createTime;
    @JsonIgnore
    @LastModifiedDate
    private Date updateTime;
    @JsonIgnore
    @CreatedBy
    private String createUser;
    @JsonIgnore
    @LastModifiedBy
    private String updateUser;
	
	@Transient
	private List<Org> orgs;
	@Transient
	private List<Role> roles;
}
