package com.fish.apple.core.web.env;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;



@Order(1)
@WebFilter(filterName="authfilter" , urlPatterns="/*")
public class EnvFilter implements Filter {

	private String contentPath ;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		contentPath = filterConfig.getServletContext().getContextPath();
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		String path = uri.substring(contentPath.length()+1);
		int index = path.indexOf("/");
		String tenantCode = path.substring(0 ,index );
		String userNo = null;
		User user = new User();
		user.setTenantCode(tenantCode);
		user.setUserNo(userNo);
		
		WebEnv webEnv = new WebEnv();
		webEnv.setUrl(path);
		webEnv.setMethod(req.getMethod());
		Environment.initEnv(webEnv, user , null);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	

}
