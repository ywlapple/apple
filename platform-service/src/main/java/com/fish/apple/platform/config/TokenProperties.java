package com.fish.apple.platform.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = TokenProperties.TOKEN_PREFIX)
@Component
public class TokenProperties {
	
	public static final String TOKEN_PREFIX = "app.token"; 
	private String key = "default" ;
	private int expireTime = 10 * 60 ; // 单位为秒
	private int freshTime = 9* 60 ; // 单位为秒
	
	private List<String> tokenExcludePatterns;
	private List<String> tokenIncludePatterns;
	private List<String> menuSignIncludePattern;
	private List<String> menuSIgnExcludePatterns;
	
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
	public List<String> getTokenExcludePatterns() {
		return tokenExcludePatterns;
	}
	public void setTokenExcludePatterns(List<String> tokenExcludePatterns) {
		this.tokenExcludePatterns = tokenExcludePatterns;
	}
	public List<String> getTokenIncludePatterns() {
		return tokenIncludePatterns;
	}
	public void setTokenIncludePatterns(List<String> tokenIncludePatterns) {
		this.tokenIncludePatterns = tokenIncludePatterns;
	}
	public List<String> getMenuSignIncludePattern() {
		return menuSignIncludePattern;
	}
	public void setMenuSignIncludePattern(List<String> menuSignIncludePattern) {
		this.menuSignIncludePattern = menuSignIncludePattern;
	}
	public List<String> getMenuSIgnExcludePatterns() {
		return menuSIgnExcludePatterns;
	}
	public void setMenuSIgnExcludePatterns(List<String> menuSIgnExcludePatterns) {
		this.menuSIgnExcludePatterns = menuSIgnExcludePatterns;
	}
	
}
