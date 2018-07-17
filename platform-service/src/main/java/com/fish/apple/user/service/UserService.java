package com.fish.apple.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.vo.User;

@Service
public class UserService {

	@Autowired
	private AccountService accountService ;
	
	private TenantSystemService systemService ;
	@Autowired
	private TokenService tokenService;
	
	public User signIn(String accountNo , String password ,String tenantNo ) {
		Account account = accountService.validate(accountNo, password);
		User user = new User();
		user.setAccount(account);
		user.setTenantNo(tenantNo);
		
		// 用户
		if(!account.getAuth()) {
			return user; // 游客用户不需要token
		}
		String personNo = account.getPersonNo();
		user.setSystems(systemService.getByPerson(tenantNo , personNo));
		
		//生成 token
		String token = tokenService.token(user);
		user.setToken(token);
		return user;
	}
}
