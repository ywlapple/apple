package com.fish.apple.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = TokenProperties.TOKEN_PREFIX)
@Component
public class TokenProperties {
	
	public static final String TOKEN_PREFIX = "app.token"; 
	private String key = "default" ;
	private int expireTime = 10 * 60 ; // 单位为秒
	private int freshTime = 9* 60 ; // 单位为秒
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public int getFreshTime() {
		return freshTime;
	}
	public void setFreshTime(int freshTime) {
		this.freshTime = freshTime;
	}
	
}
