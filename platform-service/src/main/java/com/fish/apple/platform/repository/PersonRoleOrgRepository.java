package com.fish.apple.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;

public interface PersonRoleOrgRepository extends JpaRepository<PersonRoleOrg, Long> {

	public PersonRoleOrg findByTenantNoAndPersonNoAndRoleNoAndOrgNo(String tenantNo, String personNo, String roleNo , String orgNo);

	public List<PersonRoleOrg> findByTenantNoAndPersonNo(String teanantNo, String personNo);

	public void deleteByTenantNoAndPersonNoAndRoleNoAndOrgNo(String teanantNo, String personNo, String roleNo , String orgNo);

	public List<PersonRoleOrg> findByTenantNoAndPersonNoAndAble(String currentTenantNo, String currentPersonNo,
			Able enable);

	
}
