package com.fish.apple.platform.bo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_platform_role")
public class Role extends Domain {
	private static final long serialVersionUID = -7657899626219005798L;

	@BId
	private String roleNo;
	private String roleName;
	private Able able;
	
	@Transient
	private List<Menu> menus;
}
