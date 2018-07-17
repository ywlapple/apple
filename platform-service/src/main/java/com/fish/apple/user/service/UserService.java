package com.fish.apple.user.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.util.CryptUtil;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.Person;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.platform.vo.Authen;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.config.TokenProperties;

@Service
public class UserService {

	@Autowired
	private AccountService accountService ;
	@Autowired
	private TenantSystemService systemService ;
	@Autowired
	private TokenProperties properties ;
	
	private String split = Constant.tokenSplit.getCode() ;
	
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
		String token = token(user);
		user.setToken(token);
		return user;
	}

	/**
	 * 	  校验 用户权限
	 *  token认证 ， 如果是菜单的话要进行menu认证，
	 *  1.验签  2.验证token过期 或者刷新token  3.验证是否需要从菜单进入，如果需要的话，是否有权限。
	 * @param authen
	 */
	public void authen(Authen authen) {
		String token = authen.getToken();
		// 验签
		String[] split2 = token.split(split);
		if(split2.length != 3) {
			throw BussinessException.create().kind(LoginException.tokenValidateFailure);
		}
		//验签
		String orig = token.substring(0, token.lastIndexOf(split));
		String hmacsha256 = CryptUtil.Hmacsha256(properties.getKey(), orig);
		if( ! hmacsha256.equals(split2[2])) {
			throw BussinessException.create().kind(LoginException.tokenValidateFailure);
		}
		//
		Decoder decoder = Base64.getDecoder();
		User user = JSONObject.parseObject(decoder.decode(split2[1]), User.class) ;
		
		Person person = user.getPerson();
		String tenantNo = user.getTenantNo() ;
		String personNo = user.getPerson().getPersonNo();
		// 查询该用户是否有这个api的权限
		// TODO 后端校验先不做
		
		String menuSign = authen.getMenuSign();
		if()
		
		
		
		Account account = user.getAccount();
		Date tokenEnd = account.getTokenEnd();
		Date now = new Date() ;
		if(now.after(tokenEnd)) {
			throw BussinessException.create().kind(LoginException.tokenExpired);
		}
		//  验证用户权限 
		
		Date refresh = account.getTokenRefresh();
		if(null != refresh && now.after(refresh) ) {
			return token(user);
		}
		
	}
	
	private boolean needMenuSign(Authen authen) {
		String url = authen.getUrl();
		List<String> tokenIncludePatterns = properties.getTokenIncludePatterns();
		boolean include = true;
		if(null == tokenIncludePatterns || tokenIncludePatterns.size() == 0) {
			include = true;
		}
		return false;
	}
	
	private String token(User user) {
		Account account = user.getAccount();
		long currentTimeMillis = System.currentTimeMillis();
		long end = currentTimeMillis + 1000l * (long)(properties.getExpireTime() );
		if(null == account.getTokenStart()) {
			account.setTokenStart(new Date(currentTimeMillis));
		}
		account.setTokenEnd(new Date(end));
		if(properties.getFreshTime()>0) {
			long fresh = currentTimeMillis + 1000l * (long)(properties.getFreshTime());
			account.setTokenRefresh(new Date(fresh));
		}else {
			account.setTokenRefresh(null) ;
		}
		String claim = JSONObject.toJSONString(user) ;
		Encoder encoder = Base64.getEncoder();
		String info = encoder.encodeToString(CryptUtil.JWT_HEADER.getBytes())+ split +encoder.encodeToString(claim.getBytes()) ;
		String signature = CryptUtil.Hmacsha256(properties.getKey() , info );
		return info + split + signature;
	}
}
