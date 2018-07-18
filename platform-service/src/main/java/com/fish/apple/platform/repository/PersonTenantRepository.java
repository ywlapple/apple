package com.fish.apple.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.rel.PersonTenant;

public interface PersonTenantRepository extends JpaRepository<PersonTenant, Long> {
	public PersonTenant findByTenantNoAndPersonNo(String tenantNo , String personNo);
	public List<PersonTenant> findByPersonNo(String personNo);
	public void deleteByTenantNoAndPersonNo(String tenantNo , String personNo);
}
