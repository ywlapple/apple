package com.fish.apple.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.System;

public interface SystemRepository extends JpaRepository<System, Long> {

	public System findByTenantNoAndSystemNo(String currentTenantNo, String systemNo);

	public void deleteBySystemNo(String systemNo);

	public List<System> findbyTenantNoIn(List<String> tenantNos);
	
}
