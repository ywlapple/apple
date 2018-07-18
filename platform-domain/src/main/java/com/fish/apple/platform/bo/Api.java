package com.fish.apple.platform.bo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.dict.HttpMethod;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platfom_api")
public class Api extends Domain {

	private static final long serialVersionUID = -8237796722683353014L;

	private String apiNo;
	private String apiName;
	private String apiPath;
	private HttpMethod httpMethod;
	
}
