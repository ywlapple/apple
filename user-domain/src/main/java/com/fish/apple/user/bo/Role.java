package com.fish.apple.user.bo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.BaseDomain;
import com.fish.apple.core.common.dict.Able;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_role")
public class Role extends BaseDomain {
	private String roleNo;
	private String roleName;
	private Able able;
	
	@Transient
	private List<Menu> menus;
}
