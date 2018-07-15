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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platform_system")
public class System extends Domain {
	private static final long serialVersionUID = -3540280103517375848L;
	@BId
	private String systemNo ; 
	private String systemName ;
	private String url ;
	private Able able;
	
	private String defaultLang;
	private String defaultLayout;
	
	@Transient
	private List<Lang> langs ; 
	@Transient
	private List<Layout> layouts ;
	
}
