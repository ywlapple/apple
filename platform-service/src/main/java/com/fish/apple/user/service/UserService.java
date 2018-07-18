package com.fish.apple.user.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;
import com.fish.apple.platform.bo.rel.RoleMenu;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.platform.vo.Authen;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.config.TokenProperties;
import com.fish.apple.user.repository.PersonRoleOrgRepository;
import com.fish.apple.user.repository.RoleMenuRepository;
import com.fish.apple.user.util.LoginUtil;

@Service
public class UserService {

	@Autowired
	private AccountService accountService ;
	@Autowired
	private TenantSystemService systemService ;
	@Autowired
	private TokenProperties properties ;
	@Autowired
	private PersonRoleOrgRepository personRoleOrgRepository ;
	@Autowired
	private RoleMenuRepository roleMenuRpository ;
	
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
		String token = LoginUtil.token(user);
		user.setToken(token);
		return user;
	}

	/**
	 * 	 校验 用户权限
	 *  token认证 ， 如果是菜单的话要进行menu认证，
	 *  1.验签  2.验证token过期 或者刷新token  3.验证是否需要从菜单进入，如果需要的话，是否有权限。
	 * @param authen
	 * @return 如果需要刷新 token ，则返回新的token
	 */
	public String authen(Authen authen) {
		String token = authen.getToken();
		// 验签
		User user = LoginUtil.inspectToken(token);
		//刷新token
		String refreshToken = LoginUtil.refreshToken(user);
		
		
		// 查询该用户是否有这个api的权限
		// TODO 后端校验先不做
		
		// 菜单校验
		String menuSign = authen.getMenuSign();
		if(!needMenuSign(authen)) return refreshToken;
		//验签
		
		String[] split3 = LoginUtil.inspectMenuSign(menuSign);
		
		// 菜单验签 ， 获取用户信息，校验权限

		//  验证用户权限 
		String orgNo = split3[0]  ;
		String roleNo = split3[1] ;
		String menuNo = split3[2] ;
		String tenantNo = user.getTenantNo() ;
		String personNo = user.getPerson().getPersonNo() ;
		
		PersonRoleOrg personRoleOrg = personRoleOrgRepository.findByTenantNoAndPersonNoAndRoleNoAndOrgNo(tenantNo, personNo, roleNo, orgNo);
		if(null == personRoleOrg ||  !Able.enable.equals(personRoleOrg.getAble())) {
			throw BussinessException.create().kind(LoginException.menuSignFailure);
		}
		RoleMenu roleMenu = roleMenuRpository.findByTenantNoAndRoleNoAndMenuNo(tenantNo, roleNo, menuNo);
		if(null == roleMenu ||Able.disable.equals( roleMenu.getAble())) {
			throw BussinessException.create().kind(LoginException.menuSignFailure);
		}
		return refreshToken ;
	}
	
	
	
	private boolean needMenuSign(Authen authen) {
		String url = authen.getUrl();
		List<String> tokenIncludePatterns = properties.getTokenIncludePatterns();
		boolean need = false ;
		if(null == tokenIncludePatterns || tokenIncludePatterns.size() == 0) {
			need = true;
		}else {
			need = (tokenIncludePatterns.stream().filter(patternExp-> Pattern.compile(patternExp).matcher(url).matches()).count())> 0l ;
		}
		if(!need) return false;
		
		List<String> tokenExcludePatterns = properties.getTokenIncludePatterns();
		if(null == tokenExcludePatterns || tokenExcludePatterns.size() == 0 ) return false;
		return tokenExcludePatterns.stream().filter(patternExp -> Pattern.compile(patternExp).matcher(url).matches()).count() == 0l ;
	}
	

}
