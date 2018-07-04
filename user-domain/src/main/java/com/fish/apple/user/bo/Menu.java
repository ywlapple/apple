package com.fish.apple.user.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_menu")
public class Menu extends Domain  {
	
	private static final long serialVersionUID = -6523249851377866288L;
    
	@BId
	private String menuNo;
	private String name;
	private String disName;
	private String url;
	
}
