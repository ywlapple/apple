package com.fish.apple.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Org;

public interface OrgRepository extends JpaRepository<Org, Long> {

	public Org findByTenantNoAndOrgNo(String currentTenantNo, String orgNo);

}
