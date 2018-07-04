package com.fish.apple.core.web.env;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
				
				return null;
			}
		};
	}
}
