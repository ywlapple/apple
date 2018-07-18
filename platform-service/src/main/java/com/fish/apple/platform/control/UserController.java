package com.fish.apple.platform.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fish.apple.core.common.api.Response;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.dict.AccountState;
import com.fish.apple.platform.repository.AccountRepository;
import com.fish.apple.platform.service.MenuService;
import com.fish.apple.platform.service.UserService;
import com.fish.apple.platform.vo.Authen;
import com.fish.apple.platform.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	private MenuService menuServcie ;
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 登录必须有 租户编码信息
	 * @param accountNo
	 * @param password
	 * @param tenantNo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST , path="signIn")
	public Response<User> signIn(@RequestParam String accountNo , @RequestParam String password , @RequestParam String tenantNo){
		User user = service.signIn(accountNo , password ,tenantNo);
		Response<User> resp = new Response<>();
		return resp.success(user);
	}
	/**
	 * 登录
	 * @param account
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST , path="signUp")
	public Response<Account> signUp(@RequestBody Account account){
		Assert.notNull(account , "帐户信息不能为空");
		Assert.notNull(account.getLoginType() , "登录类型不能为空");
		Assert.hasLength(account.getLoginName(), "登录名不能为空");
		Assert.hasLength(account.getPassword(), "密码不能为空");
		account.setAccountState(AccountState.init);
		account = accountRepository.saveAndFlush(account);
		Response<Account> resp = new Response<>();
		return resp.success(account);
	}
	
	@RequestMapping(method = RequestMethod.GET , path="/menus")
	public Response<Map<String, Object>> getMenus(@RequestParam String systemNo){
		Assert.notNull(systemNo , "系统编号不能为空");
		Map<String, Object> list = menuServcie.getValidMenuGroupByOrg(systemNo);
		Response<Map<String, Object>> resp = new Response<>();
		return resp.success(list);
	}
	
	/**
	 * 校验 用户权限
	 * token认证 ， 如果是菜单的话要进行menu认证，
	 * 1.验签  2.验证token过期 或者刷新token  3.验证是否需要从菜单进入，如果需要的话，是否有权限。
	 * @param systemNo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST , path="/auth")
	public Response<String> authen(@RequestParam Authen authen){
		String token = service.authen(authen);
		Response<String> resp = new Response<>();
		return resp.success(token);
	}
}
