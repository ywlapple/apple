package com.fish.apple.core.web.repository;

import javax.persistence.PrePersist;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.core.web.env.Environment;


@Component
public class BIdListener {
	
	
	@PrePersist
	public void touchForCreate(Object target) {

		
		if(target instanceof Domain ) {
			Domain domain = (Domain)target;
			domain.setTenantCode(Environment.currentTenantCode());
		}
		Class<? extends Object> class1 = target.getClass();
		String className = class1.getName();
		String field = BIdProperties.getDomainInfo().get(className);
		if(null == field) return ;
		String idName = BIdProperties.getBIdInfo().get(className) ;
		if(null == idName || idName.trim().length() == 0 ) {
			idName = Constant.commonId.getCode() ;
		}
		String no = IdFactory.getId(idName);
		BeanWrapperImpl wrapper = new BeanWrapperImpl(target);
		wrapper.setPropertyValue(field, no);
	}
	
}
