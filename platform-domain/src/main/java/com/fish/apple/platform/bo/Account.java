package com.fish.apple.platform.bo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.platform.dict.AccountState;
import com.fish.apple.platform.dict.LoginType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platfom_account")
public class Account extends Domain {
	private static final long serialVersionUID = 6650618645164486002L;

	@BId
	private String accountNo ;
	private LoginType loginType;
	private String loginName ;
	private String password  ;
	private String loginInfo ;
	private AccountState accountState ;
	
	private Boolean auth ;
	private String personNo;
	
	@Transient
	private Date tokenStart;
	@Transient
	private Date tokenEnd;
	@Transient
	private Date tokenRefresh;
	
}
