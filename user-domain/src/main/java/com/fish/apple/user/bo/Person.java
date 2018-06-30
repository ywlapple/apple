package com.fish.apple.user.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fish.apple.core.common.BaseDomain;
import com.fish.apple.core.common.dict.Sex;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="t_user_person")
public class Person extends BaseDomain {
	
	private String name;
	private Sex sex;
	private Date birthDay;
	private String telephone;
	private String email;
	
	@Transient
	private List<Org> orgs;
	@Transient
	private List<Role> roles;
}
