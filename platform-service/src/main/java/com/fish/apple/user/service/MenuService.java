package com.fish.apple.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Menu;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;
import com.fish.apple.platform.bo.rel.RoleMenu;
import com.fish.apple.user.repository.MenuRepository;
import com.fish.apple.user.repository.PersonRoleOrgRepository;
import com.fish.apple.user.repository.RoleMenuRepository;

@Service
public class MenuService {
	@Autowired
	private MenuRepository repository ;
	@Autowired
	private PersonRoleOrgRepository personRoleOrgRepository ;
	@Autowired
	private RoleMenuRepository roleMenuRepository ; 
	
	public List<Menu> getMenu(String systemNo){
		
		List<PersonRoleOrg> personRoleOrgs = personRoleOrgRepository.findByTenantNoAndPersonNoAndAble(Environment.currentTenantNo(), Environment.currentPersonNo() , Able.enable);
		if(null == personRoleOrgs || personRoleOrgs.size() == 0 ) return null ;
		List<String> roleNos = personRoleOrgs.stream().map(PersonRoleOrg::getRoleNo).collect(Collectors.toList());
		List<RoleMenu> roleMenus = roleMenuRepository.findByTenantNoAndRoleNoInAndAble(Environment.currentTenantNo() , roleNos , Able.enable);
		if(null == roleMenus || roleMenus.size() == 0) return null;
		List<String> menuNos = roleMenus.stream().map(RoleMenu::getMenuNo).collect(Collectors.toList());
		List<Menu> menuList = repository.findByTenantNoAndSystemNoAndMenuNoIn(Environment.currentTenantNo(), systemNo, menuNos) ;
		if(null == menuList || menuList.size() == 0) return null ;	
		
		
		
		return null;
	}
	

	
}
