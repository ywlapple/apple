package com.fish.apple.core.web.repository;

import javax.persistence.PrePersist;

import org.springframework.beans.BeanWrapperImpl;

import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.core.web.env.Environment;


public class BIdListener {
	
	
	@SuppressWarnings("unlikely-arg-type")
	@PrePersist
	public void touchForCreate(Object target) {

		
		if(target instanceof Domain ) {
			Domain domain = (Domain)target;
			domain.setTenantCode(Environment.currentTenantCode());
		}
		Class<? extends Object> class1 = target.getClass();
		
		String field = BIdProperties.getDomain().get(class1);
		if(null == field) return ;
		String idName = BIdProperties.getBId().get(class1) ;
		if(null == idName || idName.trim().length() == 0 ) {
			idName = Constant.commonId.getCode() ;
		}
		String no = IdFactory.getId(idName);
		BeanWrapperImpl wrapper = new BeanWrapperImpl(class1);
		wrapper.setPropertyValue(field, no);
	}
	
}
