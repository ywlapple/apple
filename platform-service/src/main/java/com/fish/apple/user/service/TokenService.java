package com.fish.apple.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.config.TokenProperties;

@Service
public class TokenService {


	
	private String MAC_INSTANCE_NAME = "HMacSHA256";
	private String header = "{\"type\":\"JWT\",\"alg\":\"HS256\"}";
	private final String split = "." ;
	
	
	public String token(User user) {
		try {
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
			String info = encoder.encodeToString(header.getBytes())+ split +encoder.encodeToString(claim.getBytes()) ;
			String signature = Hmacsha256(properties.getKey() , info );
			return info + split +  signature;
		}catch (Exception e) {
			throw BussinessException.create(e).kind(LoginException.tokenError);
		}
	}
	public String validate(String token ,boolean autoFresh) {
		
		try {
			String[] split2 = token.split(split);
			if(split2.length != 3) {
				throw BussinessException.create().kind(LoginException.tokenValidateFailure);
			}
			//验签
			String orig = split2[0] + split + split2[1] ;
			String hmacsha256 = Hmacsha256(properties.getKey() , orig);
			if( ! hmacsha256.equals(split2[2])) {
				throw BussinessException.create().kind(LoginException.tokenValidateFailure);
			}
			// 解析用户信息
			
			Decoder decoder = Base64.getDecoder();
			if( !header.equals(new String(decoder.decode(split2[0])))) {
				throw BussinessException.create().kind(LoginException.tokenValidateFailure); 
			}
			User user = JSONObject.parseObject(decoder.decode(split2[1]), User.class) ;
			
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
		} catch(InvalidKeyException | NoSuchAlgorithmException e ) {
			throw BussinessException.create("token("+ token + ")验签错误" ,e).kind(LoginException.tokenValidateError);
		} 
		return token;
	}

	public String menuSign(String orgNo , String roleNo , String menuNo ) {
		String info = orgNo + split + roleNo +split + menuNo ; 
		String signature;
		try {
			signature = Hmacsha256(properties.getKey() , info );
		} catch (Exception e) {
			throw BussinessException.create(e).kind(LoginException.menuSignError);
		} 
		return info+split+signature ;
	}
	
	public void validateMenuSign(String menuSign) {
		try {
			int indexOf = menuSign.lastIndexOf(split);
			String info = menuSign.substring(0, indexOf);
			String hmacsha256 = Hmacsha256(properties.getKey() , info);
			if( ! hmacsha256.equals(menuSign.substring(indexOf+1))) {
				throw BussinessException.create().kind(LoginException.tokenValidateFailure);
			}
		} catch(InvalidKeyException | NoSuchAlgorithmException e ) {
			throw BussinessException.create("菜单签名("+ menuSign + ")验签错误" ,e).kind(LoginException.tokenValidateError);
		} 
	}
	
}
