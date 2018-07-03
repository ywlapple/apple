package com.fish.apple.core.web.repository;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

@Component
public class BIdInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 3084916375179655414L;

	@Override
	public boolean onSave(
			Object entity, 
			Serializable id, 
			Object[] state, 
			String[] propertyNames, 
			Type[] types) {
		System.out.println("==============================");
		System.out.println(propertyNames);
		System.out.println("==============================");
		
		
		return false;
	}
	
}
