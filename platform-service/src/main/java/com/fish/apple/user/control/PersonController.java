package com.fish.apple.user.control;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fish.apple.core.common.api.Response;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.Person;
import com.fish.apple.platform.dict.AccountState;
import com.fish.apple.user.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonRepository repository ; 
	
	private personservice
	
	@RequestMapping(method = RequestMethod.GET )
	public Response<Person> save(@RequestBody Person person){
		Assert.notNull(person , "帐户信息不能为空");
		Assert.notNull(person.getName() , "用户名称不能为空");
		person = repository.saveAndFlush(person);
		
		Response<Person> resp = new Response<>();
		return resp.success(person);
	}
	
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<Account> save(@RequestBody Account account){
		Assert.notNull(account , "帐户信息不能为空");
		Assert.notNull(account.getLoginType() , "登录类型不能为空");
		Assert.hasLength(account.getLoginName(), "登录名不能为空");
		Assert.hasLength(account.getPassword(), "密码不能为空");
		account.setAccountState(AccountState.init);
		account = accountRepository.saveAndFlush(account);
		Response<Account> resp = new Response<>();
		return resp.success(account);
	}
	
	@RequestMapping(method = RequestMethod.PUT )
	public Response<Account> save(@RequestBody Person person){
		Assert.notNull(person , "用户信息不能为空");
		Assert.notNull(account.getLoginType() , "登录类型不能为空");
		Assert.hasLength(account.getLoginName(), "登录名不能为空");
		Assert.hasLength(account.getPassword(), "密码不能为空");
		account.setAccountState(AccountState.init);
		account = accountRepository.saveAndFlush(account);
		Response<Account> resp = new Response<>();
		return resp.success(account);
	}	
	
	
	
}
