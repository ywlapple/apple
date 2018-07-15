package com.fish.apple.platform.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_platform_menu")
public class Menu extends Domain  {
	
	private static final long serialVersionUID = -6523249851377866288L;
    
	private String systemNo;
	@BId
	private String menuNo;
	private String name;
	private String disName;
	private String url;
	
}
