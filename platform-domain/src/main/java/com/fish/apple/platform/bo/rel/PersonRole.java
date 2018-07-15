package com.fish.apple.platform.bo.rel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.common.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="t_platform_rel_person_role")
public class PersonRole extends Domain  {
	private static final long serialVersionUID = 3785278592868651160L;

	private String personNo;
	private String roleNo;
	private Able able;
	private Date startDate;
	private Date endDate;
	private String desc;
	
}
