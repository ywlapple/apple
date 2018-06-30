package com.fish.apple.user.bo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.BaseDomain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_account")
public class Account extends BaseDomain {
	private String name;
	private String password;
	
	@Transient
	private Person person;
	
	
}
