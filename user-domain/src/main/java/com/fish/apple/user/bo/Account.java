package com.fish.apple.user.bo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_user_account")
public class Account extends Domain {
	private static final long serialVersionUID = 6650618645164486002L;

	private String name;
	private String password;
	
	@Transient
	private Person person;
	
	
}
