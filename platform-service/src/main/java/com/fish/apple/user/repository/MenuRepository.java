package com.fish.apple.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.platform.bo.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	public Menu findByTenantNoAndMenuNo(String currentTenantNo, String menuNo);

	public List<Menu> findByTenantNoAndSystemNo(String currentTenantNo, String systemNo);
	public List<Menu> findByTenantNoAndSystemNoAndAble(String currentTenantNo, String systemNo , Able able);
	public List<Menu> findByTenantNoAndSystemNoAndMenuNoIn(String currentTenantNo, String systemNo , List<String> menuNos);
	
}
