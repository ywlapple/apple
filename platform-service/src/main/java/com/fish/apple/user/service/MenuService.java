package com.fish.apple.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
	
	public List<Menu> getValidMenuGroupByOrg(String systemNo){
		
		List<PersonRoleOrg> personRoleOrgs = personRoleOrgRepository.findByTenantNoAndPersonNoAndAble(Environment.currentTenantNo(), Environment.currentPersonNo() , Able.enable);
		if(null == personRoleOrgs || personRoleOrgs.size() == 0 ) return null ;
		List<String> roleNos = personRoleOrgs.stream().map(PersonRoleOrg::getRoleNo).collect(Collectors.toList());
		List<RoleMenu> roleMenus = roleMenuRepository.findByTenantNoAndRoleNoInAndAble(Environment.currentTenantNo() , roleNos , Able.enable);
		if(null == roleMenus || roleMenus.size() == 0) return null;
//		List<Menu> menuList = repository.findByTenantNoAndSystemNoAndMenuNoIn(Environment.currentTenantNo(), systemNo, menuNos) ;
	//	if(null == menuList || menuList.size() == 0) return null ;	
		// 按照 机构编号  组装 menuList
		Map<String, List<PersonRoleOrg>> orgMap = personRoleOrgs.stream().collect(Collectors.groupingBy(PersonRoleOrg::getOrgNo));
		Map<String, List<Menu>> orgMenuMap = new HashMap<>();
		orgMap.values().forEach( jobs -> { 
			String orgNo  =jobs.get(0).getOrgNo();
			List<String> roleNosInOrg = jobs.stream().map(PersonRoleOrg::getRoleNo).collect(Collectors.toList()) ;
			List<String> menuNosInOrg = roleMenus.stream().filter( roleMenu -> roleNosInOrg.contains(roleMenu.getRoleNo()) ).map(RoleMenu::getMenuNo).collect(Collectors.toList());
			List<Menu> treeList = treeMenu(systemNo , menuNosInOrg);
			orgMenuMap.put(orgNo, treeList) ;
		} );
		//排序 ， 加签名
//		orgMenuMap
		
		return null;
	}
	
	private void sortTree(Menu menu , List<PersonRoleOrg> personRoleOrgs) {
		
		List<Menu> children = menu.getChildren();
		if(null == children || children.size() == 0 ) {
			return null ;
		}
		
	}
	
	

	private List<Menu> treeMenu(String systemNo ,List<String> menuNos){
		if(null == menuNos || menuNos.size() == 0) return null ;
		List<Menu> menuList = repository.findByTenantNoAndSystemNoAndAble(Environment.currentTenantNo(), systemNo , Able.enable);
		if(null == menuList || menuList.size() == 0)  return null ;
		List<Menu> leafMenuList = menuList.stream().filter(menu -> menuNos.contains(menu.getMenuNo()) ).collect(Collectors.toList());
		Set<Menu> parentList = new HashSet<>();
		leafMenuList.forEach( menu -> {
			parentList.add(lookupMother(menuList, menu));
		});
		return new ArrayList<>(parentList);
	}
	public Menu lookupMother(List<Menu> allMenu , Menu menu) {
		Optional<Menu> parentMenuOp = allMenu.stream().filter( menuU -> menuU.getMenuNo().equals(menu.getParentMenuNo()) ).findFirst();
		if(parentMenuOp.isPresent()) {
			Menu parentMenu = parentMenuOp.get();
			List<Menu> children = parentMenu.getChildren();
			if(null == children) {
				children = new ArrayList<>();
				parentMenu.setChildren(children);
			}
			children.add(menu);
			return lookupMother(allMenu , parentMenu);
		}else {
			return menu ;
		}
	}
	
}
