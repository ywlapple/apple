package com.fish.apple.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.Menu;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;
import com.fish.apple.platform.bo.rel.RoleMenu;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.repository.MenuRepository;
import com.fish.apple.user.repository.PersonRoleOrgRepository;
import com.fish.apple.user.repository.RoleMenuRepository;

@Service
public class UserService {

	@Autowired
	private AccountService accountService ;
	
	private TenantSystemService systemService ;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MenuRepository menuRepository ;
	@Autowired
	private PersonRoleOrgRepository personRoleOrgRepository ;
	@Autowired
	private RoleMenuRepository roleMenuRepository ; 
	
	
	
	/**
	 * 全量保存用户
	 * 包括
	 * @param user
	 * @return
	 */
	public boolean saveUser ( User user ) {
		 // 保存 用户信息 
		
		// 跟新账号信息中的用户关联
		
		// 分配所属 系统信息
		
		// 
		
		
		
		return false;
	}
	
	public User getUser(String userId) {
		
	
		return null ;
	}
	
	public User signIn(String accountNo , String password ) {
		Account account = accountService.validate(accountNo, password);
		User user = new User();
		user.setAccount(account);
		
		// 用户
		if(!account.getAuth()) {
			return user; // 游客用户不需要token
		}
		String personNo = account.getPersonNo();
		String tenantNo = Environment.currentTenantNo() ;
		if(com.fish.apple.core.web.env.User.sysTenantNo.equals(tenantNo)) {
			// 主页登录 ，全局查询系统
			tenantNo  =null ; 
		}
		user.setSystems(systemService.getByPerson(tenantNo , personNo));
		
		//生成 token
		String token = tokenService.token(user);
		user.setToken(token);
		return user;
	}

	public List<Menu> getMenu(String systemNo) {
		// 查询 该系统下 有权限的 菜单
		


		List<PersonRoleOrg> personRoleOrgs = personRoleOrgRepository.findByTenantNoAndPersonNoAndAble(Environment.currentTenantNo(), Environment.currentPersonNo() , Able.enable);
		if(null == personRoleOrgs || personRoleOrgs.size() == 0 ) return null ;
		List<String> roleNos = personRoleOrgs.stream().map(PersonRoleOrg::getRoleNo).collect(Collectors.toList());
		List<RoleMenu> roleMenus = roleMenuRepository.findByTenantNoAndRoleNoInAndAble(Environment.currentTenantNo() , roleNos , Able.enable);
		if(null == roleMenus || roleMenus.size() == 0) return null;
		List<String> menuNos = roleMenus.stream().map(RoleMenu::getMenuNo).collect(Collectors.toList());
		List<Menu> menuList = menuRepository.findByTenantNoAndSystemNoAndMenuNoIn(Environment.currentTenantNo(), systemNo, menuNos) ;
		if(null == menuList || menuList.size() == 0) return null ;
		
		// 组装menu树结构
		
		
		// 关联 菜单 的 personroleorg 
		
		// 填充菜单校验码
		
		return null;
	}
}
