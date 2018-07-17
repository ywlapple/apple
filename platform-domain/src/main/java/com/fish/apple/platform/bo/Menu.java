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
@Table(name="t_platform_menu")
public class Menu extends Domain  {
	
	private static final long serialVersionUID = -6523249851377866288L;
    
	private String systemNo;
	@BId
	private String menuNo;
	private String menuName;
	private String disName;
	private String parentMenuNo ; 
	private String preMenuNo;
	private String url;
	private Able able;
	private Integer sort ;
	
	@Transient
	private List<Menu> children;
	@Transient
	private String sign;
}
