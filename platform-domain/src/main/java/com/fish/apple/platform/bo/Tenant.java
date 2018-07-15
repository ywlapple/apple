package com.fish.apple.platform.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platfom_tenant")
public class Tenant extends Domain {

	private static final long serialVersionUID = 9199101828681864740L;
	private String tenantName ;
	private String desc;
}
