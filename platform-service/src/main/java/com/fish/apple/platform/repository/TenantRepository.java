package com.fish.apple.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

	public Tenant findByTenantNo(String tenantNo);
}
