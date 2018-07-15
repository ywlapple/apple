package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByTenantNoAndRoleNo(String currentTenantNo, String roleNo);

}
