package com.fish.apple.user.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.BaseDomain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_org")
public class Org extends BaseDomain{

	private String orgNo;
	private String orgName;
	private Integer orgLevel;
	
}
