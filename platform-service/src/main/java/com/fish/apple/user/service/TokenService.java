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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.exception.LoginException;
import com.fish.apple.platform.vo.User;
import com.fish.apple.user.config.TokenProperties;

@Service
public class TokenService {

	@Autowired
	private TokenProperties properties ;
	
	private String MAC_INSTANCE_NAME = "HMacSHA256";
	private String header = "{\"type\":\"JWT\",\"alg\":\"HS256\"}";
	private final String split = "." ;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
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
			String claim = mapper.writeValueAsString(user) ;
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
			User user = mapper.readValue(decoder.decode(split2[1]), User.class) ;
			
			Account account = user.getAccount();
			Date tokenEnd = account.getTokenEnd();
			Date now = new Date() ;
			if(now.after(tokenEnd)) {
				throw BussinessException.create().kind(LoginException.tokenExpired);
			}
			Date refresh = account.getTokenRefresh();
			if(null != refresh && now.after(refresh) ) {
				return token(user);
			}
		} catch(UnsupportedEncodingException e) {
			throw BussinessException.create("token("+ token + ")解析错误" ,e).kind(LoginException.tokenValidateError);
			
		} catch(InvalidKeyException | NoSuchAlgorithmException e ) {
			throw BussinessException.create("token("+ token + ")验签错误" ,e).kind(LoginException.tokenValidateError);
		} catch (IOException e) {
			throw BussinessException.create("token("+ token + ")解析用户信息错误" ).kind(LoginException.tokenValidateError) ;
		} 
		return token;
	}

	private String Hmacsha256(String secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
	    Mac hmac_sha256 = Mac.getInstance(MAC_INSTANCE_NAME);
	    SecretKeySpec key = new SecretKeySpec(secret.getBytes(), MAC_INSTANCE_NAME);
	    hmac_sha256.init(key);
	    byte[] buff = hmac_sha256.doFinal(message.getBytes());
	    return Base64.getEncoder().encodeToString(buff);
	}
	
	
}
