package com.fish.apple.core.web.env;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.fish.apple.core.common.constant.Constant;

@Configuration
@ComponentScan(basePackages="com.fish.apple.core.web.env")
public class EnvConfigure {

	@Bean
	@ConditionalOnMissingBean(EnvFactory.class)
	public EnvFactory createEnvFactory() {
		return new EnvFactory() {
			
			@Override
			public WebEnv fillWeb(HttpServletRequest request) {
				
				WebEnv webEnv = new WebEnv();
				webEnv.setHttpMethod(request.getMethod());
				webEnv.setContentPath(request.getContextPath());
				webEnv.setPath(request.getServletPath());
				return webEnv;
			}
			
			@Override
			public User fillUser(HttpServletRequest request) {
				String method = request.getMethod();
				String token = null ; 
				String menuSign = null;
				if("get".equalsIgnoreCase(method)) {
					token = request.getParameter(Constant.tokenKey.getCode());
					menuSign = request.getParameter(Constant.menuSignKey.getCode());
				}
				
				if(StringUtils.isBlank( token)) {
					token = request.getHeader(Constant.tokenKey.getCode());
				}
				if(StringUtils.isBlank( token)) {
					Cookie[] cookies = request.getCookies();
					for(Cookie c: cookies) {
						if(Constant.tokenKey.getCode().equals(c.getName())) {
							token  = c.getValue();
						}
					}
				}
				if(StringUtils.isBlank( menuSign)) {
					menuSign = request.getHeader(Constant.menuSignKey.getCode());
				}
				if(StringUtils.isBlank( menuSign)) {
					Cookie[] cookies = request.getCookies();
					for(Cookie c: cookies) {
						if(Constant.menuSignKey.getCode().equals(c.getName())) {
							menuSign  = c.getValue();
						}
					}
				}
				
				if(StringUtils.isNotBlank(token)) {
					User user = new User();
					String[] split = token.split(".");
					String string = split[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decode = decoder.decode(string);
					JSONObject userMap=(JSONObject) JSONObject.parse(new String(decode));
					String tenantNo = userMap.getString("tenantNo");
					JSONObject accountMap = (JSONObject)userMap.get("account");
					JSONObject personMap = (JSONObject)userMap.get("person");
					String personNo = personMap.getString("personNo");
					String accountNo = accountMap.getString("accountNo");
					
					user.setTenantNo(tenantNo);
					user.setPersonNo(personNo);
					user.setAccountNo(accountNo);
					if(StringUtils.isNotBlank(menuSign)) {
						String[] split2 = token.split(".");
						String orgNo = split2[0];
						String roleNo = split2[1];
						String menuNo = split2[2];
						user.setOrgNo(orgNo);
						user.setMenuNo(menuNo);
						user.setRoleNo(roleNo);
					}
					return user;
				}
				return null;
			}
		};
	}
}
