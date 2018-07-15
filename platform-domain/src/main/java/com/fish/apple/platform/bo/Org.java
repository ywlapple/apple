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
@Table(name="t_platform_org")
public class Org extends Domain{
	private static final long serialVersionUID = 3137409145046485473L;

	@BId
	private String orgNo;
	private String orgName;
	private Integer orgLevel;
	private Long parentOrgNo;
	private Able able;
	
	@Transient
	private List<Org> orgs ;
	
}
