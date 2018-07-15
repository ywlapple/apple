package com.fish.apple.core.web.repository;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.BeanWrapperImpl;

import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.domain.BIdable;
import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.core.web.env.Environment;


public class BIdListener {
	
	@PrePersist
	public void touchForCreate(Object target) {
		
		Date now = new Date() ;
		String operator = Environment.currentPersonNo() ;
		if(target instanceof Domain ) {
			Domain domain = (Domain)target;
			domain.setTenantNo(Environment.currentTenantNo());
			domain.setCreateTime(now);
			domain.setCreateUser(operator);
			domain.setUpdateTime(now);
			domain.setUpdateUser(operator);
		}
		if(target instanceof BIdable) {
			BIdable bIdable = (BIdable) target;
			if(null == bIdable.getBId() || bIdable.getBId().trim().length() == 0) {
				bIdable.setBId(IdFactory.getId(bIdable.getBIdName()));
			}
			
		}else {
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
			Object propertyValue = wrapper.getPropertyValue(field);
			if(null == propertyValue || ((String)propertyValue).trim().equals("") ) {
				wrapper.setPropertyValue(field, no);
			}
		}
	}
	
	@PreUpdate
	public void touchUpdate(Object target ) {
		if(target instanceof Domain ) {
			Date now = new Date() ;
			String operator = Environment.currentPersonNo() ;
			Domain domain = (Domain)target;
			domain.setTenantNo(Environment.currentTenantNo());
			domain.setUpdateTime(now);
			domain.setUpdateUser(operator);
		}
	}
	
	@PreRemove
	public void touchDelete(Object target ) {
		if(target instanceof Domain ) {
			Domain domain = (Domain)target;
			domain.setTenantNo(Environment.currentTenantNo());
		}
	}
	
	public void touchQuery(Object target ) {
		if(null != target && target instanceof Domain) {
			Domain domain = (Domain)target;
			domain.setTenantNo(Environment.currentTenantNo());
		}
		// 查询方法 自动增加tenantNo 参数 ， TODO 
	}
	
}
