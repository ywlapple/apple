package com.fish.apple.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.platform.bo.System ;
import com.fish.apple.platform.bo.rel.PersonTenant;
import com.fish.apple.user.repository.PersonTenantRepository;
import com.fish.apple.user.repository.SystemRepository;

public class TenantSystemService {
	@Autowired
	private PersonTenantRepository relRepository ;
	@Autowired
	private SystemRepository systemRepository ; 
	
	public List<System> getByPerson(String tenantNo ,  String personNo){
		PersonTenant qry = new PersonTenant();
		qry.setAble(Able.enable);
		qry.setTenantNo(tenantNo);
		qry.setPersonNo(personNo);
		Example<PersonTenant> example = Example.of(qry) ; 
		List<PersonTenant> personTenants = relRepository.findAll(example);
		if(null == personTenants || personTenants.size() == 0) {
			return null ; 
		}
		List<String> tenantNos = personTenants.stream().map(PersonTenant::getTenantNo).collect(Collectors.toList());
		List<System> systems = systemRepository.findbyTenantNoIn(tenantNos);
		if(null == systems || systems.size() == 0) return null;
		return systems;
	}
	
	
}
