package com.fish.apple.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.System;

public interface SystemRepository extends JpaRepository<System, Long> {

	List<System> findbyTenantNoIn(List<String> tenantNos);
	
}
