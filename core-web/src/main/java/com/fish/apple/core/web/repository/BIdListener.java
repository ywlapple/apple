package com.fish.apple.core.web.repository;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Configurable;

import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.core.web.env.Environment;

@Configurable
public class BIdListener {
	@PrePersist
	public void touchForCreate(Object target) {

		
		if(target instanceof Domain ) {
			Domain domain = (Domain)target;
			domain.setTenantCode(Environment.currentTenantCode());
		}
		
	}
	
}
