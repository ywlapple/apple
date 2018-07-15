package com.fish.apple.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.dict.AccountState;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.user.repository.AccountRepository;
import com.fish.apple.user.util.LoginUtil;

public class AccountService {
	@Autowired
	private AccountRepository repository;
	
	public Account validate(String accountNo , String password) {
		Account account = repository.findByTenantNoAndAccountNo(Environment.currentTenantNo() , accountNo);
		if(null == account) {
			throw BussinessException.create().kind(LoginException.accountUnExist).msg(account) ;
		}
		if(!LoginUtil.validate(password, account.getPassword())) {
			throw BussinessException.create().kind(LoginException.passwordError).msg(account) ;
		}
		
		AccountState accountState = account.getAccountState();
		switch (accountState) {
		case init:
			throw BussinessException.create().kind(LoginException.accountUnActive).msg(account) ;
		case freeze:
			throw BussinessException.create().kind(LoginException.accountFreeze).msg(account) ;
		case finish:
			throw BussinessException.create().kind(LoginException.accountFinish).msg(account) ;
		default:
			break;
		}
		return account ;
	}
	
	public Account create( Account account ) {
		
		return account;
	}
	
}
