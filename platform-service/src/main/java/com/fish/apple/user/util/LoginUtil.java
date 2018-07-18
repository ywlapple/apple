package com.fish.apple.user.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.util.CryptUtil;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.config.TokenProperties;

@Component
public class LoginUtil {

	private static TokenProperties properties ; 
	
	@Autowired
	public void setProperties(TokenProperties properties) {
		LoginUtil.properties = properties;
	}

	private static String split = Constant.tokenSplit.getCode() ;
	public static boolean validate(String login , String store) {
		return StringUtils.equals(login, store);
	}
	
	public static String menuSign(String orgNo , String roleNo , String menuNo ) {
		String info = orgNo + split + roleNo +split + menuNo ; 
		String signature;
		try {
			signature = CryptUtil.Hmacsha256(properties.getKey() , info );
		} catch (Exception e) {
			throw BussinessException.create(e).kind(LoginException.menuSignError);
		} 
		return info+split+signature ;
	}
	
	public static String token(User user) {
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
	
	public static User inspectToken(String token) {
		
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
		Account account = user.getAccount();
		Date tokenEnd = account.getTokenEnd();
		Date now = new Date() ;
		if(now.after(tokenEnd)) {
			throw BussinessException.create().kind(LoginException.tokenExpired);
		}
		return user;
	}
	
	public static String refreshToken(User user) {
		Account account = user.getAccount();
		Date refresh = account.getTokenRefresh();
		String newToken = null ;
		if(null != refresh && new Date().after(refresh) ) {
			newToken = LoginUtil.token(user);
			return newToken ;
		}
		return null;
	}
	
	public static String[] inspectMenuSign(String menuSign) {
		String[] split3 = menuSign.split(split);
		if(split3.length != 4) {
			throw BussinessException.create().kind(LoginException.menuSignFailure);
		}
		String claim = menuSign.substring(0 , menuSign.lastIndexOf(split));
		
		String hmacsha2562 = CryptUtil.Hmacsha256(properties.getKey(), claim);
		if(!hmacsha2562.equals(split3[3])) {
			throw BussinessException.create().kind(LoginException.menuSignFailure);
		}
		return split3;
	}
}
