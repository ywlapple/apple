package com.fish.apple.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.platform.bo.rel.RoleMenu;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {


	public List<RoleMenu> findByTenantNoAndRoleNo(String tenantNo, String roleNo);

	public RoleMenu findByTenantNoAndRoleNoAndMenuNo(String tenantNo, String roleNo, String menuNo);

	public void deleteByTenantNoAndRoleNoAndMenuNo(String tenantNo, String roleNo, String menuNo);

	public List<RoleMenu> findByTenantNoAndRoleNoIn(String currentTenantNo, List<String> roleNos);

	public List<RoleMenu> findByTenantNoAndRoleNoInAndAble(String currentTenantNo, List<String> roleNos, Able enable);
	
	
	

}
