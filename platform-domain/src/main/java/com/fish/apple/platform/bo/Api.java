package com.fish.apple.platform.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platfom_Layout")
public class Api extends Domain {
	private static final long serialVersionUID = 6231900016279280198L;
	
	@BId
	private String apiNo;
	private String url;
	
}
