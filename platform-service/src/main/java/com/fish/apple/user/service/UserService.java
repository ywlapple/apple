package com.fish.apple.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.vo.User;

@Service
public class UserService {

	@Autowired
	private AccountService accountService ;
	
	private TenantSystemService systemService ;
	@Autowired
	private TokenService tokenService;
	
	
	
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
}
