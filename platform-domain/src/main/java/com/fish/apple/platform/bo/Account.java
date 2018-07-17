package com.fish.apple.platform.bo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.BIdable;
import com.fish.apple.platform.dict.AccountState;
import com.fish.apple.platform.dict.LoginType;

import lombok.Data;

@Data
@Entity
@Table(name="t_platfom_account")
public class Account implements BIdable {
	private static final long serialVersionUID = 6650618645164486002L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@BId
	private String accountNo ;
	private LoginType loginType;
	private String loginName ;
	private String password  ;
	private String loginInfo ;
	private AccountState accountState ;
	
	private Boolean auth ;
	private String personNo;
	
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private String createUser;
    @JsonIgnore
    private String updateUser;
	
	@Transient
	private Date tokenStart;
	@Transient
	private Date tokenEnd;
	@Transient
	private Date tokenRefresh;
	@Override
	public String getBIdName() {
		return "AccountNo";
	}
	@Override
	public String getBId() {
		return this.getAccountNo();
	}
	@Override
	public void setBId(String bId) {
		this.setAccountNo(bId);
	}
}
