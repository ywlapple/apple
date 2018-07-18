package com.fish.apple.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Menu;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;
import com.fish.apple.platform.bo.rel.RoleMenu;
import com.fish.apple.user.repository.MenuRepository;
import com.fish.apple.user.repository.PersonRoleOrgRepository;
import com.fish.apple.user.repository.RoleMenuRepository;
import com.fish.apple.user.util.LoginUtil;

@Service
public class MenuService {
	@Autowired
	private MenuRepository repository ;
	@Autowired
	private PersonRoleOrgRepository personRoleOrgRepository ;
	@Autowired
	private RoleMenuRepository roleMenuRepository ; 
	
	public Map<String, Object> getValidMenuGroupByOrg(String systemNo){
		
		List<PersonRoleOrg> personRoleOrgs = personRoleOrgRepository.findByTenantNoAndPersonNoAndAble(Environment.currentTenantNo(), Environment.currentPersonNo() , Able.enable);
		if(null == personRoleOrgs || personRoleOrgs.size() == 0 ) return null ;
		List<String> roleNos = personRoleOrgs.stream().map(PersonRoleOrg::getRoleNo).collect(Collectors.toList());
		List<RoleMenu> roleMenus = roleMenuRepository.findByTenantNoAndRoleNoInAndAble(Environment.currentTenantNo() , roleNos , Able.enable);
		if(null == roleMenus || roleMenus.size() == 0) return null;
		// 按照 机构编号  组装 menuList
		Map<String, List<PersonRoleOrg>> orgMap = personRoleOrgs.stream().collect(Collectors.groupingBy(PersonRoleOrg::getOrgNo));
		Map<String, Object> orgMenuMap = new HashMap<>();
		orgMap.entrySet().forEach( entry -> { 
			List<Menu> treeMenu = treeMenu(systemNo, roleMenus, entry.getValue());
			orgMenuMap.put(entry.getKey(), JSONArray.toJSON(treeMenu));
		} );
		return orgMenuMap;
	}

	private List<Menu> treeMenu(String systemNo , List<RoleMenu> roleMenus ,List<PersonRoleOrg> personRoleOrgsInOrg   ){
		if(null == roleMenus || roleMenus.size() == 0 || personRoleOrgsInOrg == null || personRoleOrgsInOrg.size() == 0) return null ;
		List<Menu> menuList = repository.findByTenantNoAndSystemNoAndAble(Environment.currentTenantNo(), systemNo , Able.enable);
		if(null == menuList || menuList.size() == 0)  return null ;
		List<Menu> leafMenuList = new ArrayList<>();
		personRoleOrgsInOrg.forEach(personRoleInOrg ->{
			List<RoleMenu> roleMenusGroupByRoleAndOrg = roleMenus.stream().filter(roleMenu->roleMenu.getRoleNo().equals(personRoleInOrg.getRoleNo())).collect(Collectors.toList()); 
			roleMenusGroupByRoleAndOrg.forEach(roleMenu->{
				List<Menu> menusGroupByOrgRole = menuList.stream().filter( menu -> menu.getMenuNo().equals(roleMenu.getMenuNo())).collect(Collectors.toList());
				menusGroupByOrgRole.forEach(menu->{
					String sign = LoginUtil.menuSign(personRoleInOrg.getOrgNo(), roleMenu.getRoleNo(),  menu.getMenuNo()) ;
					menu.setSign(sign);
					leafMenuList.add(menu);
				});
			});
		});
		Set<Menu> parentSet = new HashSet<>();
		leafMenuList.forEach( menu -> {
			parentSet.add(lookupMother(menuList, menu));
		});
		List<Menu> parentList = parentSet.stream().sorted( (a,b) -> {
			int aa = a.getSort();
			int bb = b.getSort();
			return aa>bb? 1:-1;
		}).collect(Collectors.toList());
		return parentList;
	}
	public Menu lookupMother(List<Menu> allMenu , Menu menu) {
		Optional<Menu> parentMenuOp = allMenu.stream().filter( menuU -> menuU.getMenuNo().equals(menu.getParentMenuNo()) ).findFirst();
		if(parentMenuOp.isPresent()) {
			Menu parentMenu = parentMenuOp.get();
			List<Menu> children = parentMenu.getChildren();
			if(null == children) {
				children = new LinkedList<Menu>();
				parentMenu.setChildren(children);
				children.add(menu);
			}else {
				int i = 0 ;
				for( ; i<children.size(); i++ ) {
					Menu child = children.get(i);
					int a= child.getSort() ; 
					int b = menu.getSort() ;
					if(b<a) {
						children.add(i, menu);
						break;
					}
				}
				if(i == children.size()) {
					children.add(menu);
				}
			}
			return lookupMother(allMenu , parentMenu);
		}else {
			return menu ;
		}
	}
	
}
