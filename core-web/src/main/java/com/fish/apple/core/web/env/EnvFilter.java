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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import com.fish.apple.core.common.constant.Constant;



@Order(1)
@WebFilter(filterName="authfilter" , urlPatterns="/*")
public class EnvFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(Constant.coreLog.getCode()) ;
	
	@Autowired
	private EnvFactory envFactory ;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("EnvFilter init .");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		if(log.isDebugEnabled()) {
			log.debug("EnvFilter intercept :" + req.getRequestURI());
		}
		WebEnv fillWeb = envFactory.fillWeb(req);
		User fillUser = envFactory.fillUser(req);
		Environment.initEnv(fillWeb, fillUser , null);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		log.info("EnvFilter destroy .");
	}

	

}
