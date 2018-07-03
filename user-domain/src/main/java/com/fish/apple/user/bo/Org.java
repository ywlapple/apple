package com.fish.apple.user.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_org")
public class Org extends Domain{
	private static final long serialVersionUID = 3137409145046485473L;

	private String orgNo;
	private String orgName;
	private Integer orgLevel;
	private Long parentOrgId;
	
}
