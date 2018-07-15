package com.fish.apple.platform.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platfom_Lang")
public class Lang extends Domain{
	
	private static final long serialVersionUID = -3180813562625981013L;
	private String systemNo ;
	private String lang ; 
	private String desc;

}
