package com.fish.apple.core.web.repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.web.repository.IdFactory.IdInfo;

@ConfigurationProperties(prefix = BIdProperties.ENTITY_PREFIX)
@Component
public class BIdProperties implements InitializingBean{

	public static final String ENTITY_PREFIX = "app.entity";
	
	/**
	 * key: class name
	 * value: sequence name
	 */
	private static Map<String, String> bIdInfo = new HashMap<>();

	/**
	 * key: class name
	 * value: business property name
	 */
	private static Map<String, String> domainInfo = new HashMap<>();
	
	private String basePackage = Constant.DomainBasePackage.getCode();
	
	private List<IdFactory.IdInfo> idInfoList ;
	
	private IdFactory.IdInfo defaultIdInfo ;
	
	
	public BIdProperties() {
		IdInfo idInfo = new IdFactory.IdInfo();
		idInfo.setName(Constant.commonId.getCode());
		idInfo.setSequenceName(Constant.commonSequence.getCode());
		idInfo.setPattern(Constant.commonIdPattern.getCode()); // date日期的格式+ 0 ，0表示要替换成数字序列
		idInfo.setPrefix(Constant.commonIdPrefix.getCode()); //prefix
		idInfo.setSuffix(Constant.commonIdSuffix.getCode()); //suffix
		this.defaultIdInfo = idInfo;
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 扫描basePackage 路径下的 entity，根据@BId获取相关信息，如果配置中已经配置，则不做覆盖
		
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		final Set<BeanDefinition> beans = provider.findCandidateComponents(basePackage);
		
		for(BeanDefinition definition : beans ) {
			Class<?> clazz = Class.forName(definition.getBeanClassName());
			findFieldByAnnotion(clazz);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void findFieldByAnnotion(Class clazz ){
		if(Object.class.equals(clazz) ) return ;
		
		Field[] declaredFields = clazz.getDeclaredFields();
		if(null != declaredFields && declaredFields.length > 0 ) {
			for(Field field : declaredFields) {
				BId bid = field.getDeclaredAnnotation(BId.class);
				if(null != bid) {
					domainInfo.putIfAbsent(clazz.getName(), field.getName());
					String idName = bid.value() ;
					if(StringUtils.isBlank(idName)) {
						idName = Constant.commonId.getCode() ;
					}
					bIdInfo.putIfAbsent(clazz.getName(), idName);
				}
			}
		}
		Class superclass = clazz.getSuperclass();
		findFieldByAnnotion(superclass );
	}
	
	
	public static Map<String, String> getBIdInfo() {
		return bIdInfo;
	}


	public void setBidInfo(Map<String, String> bidInfo) {
		bIdInfo = bidInfo;
	}


	public static Map<String, String> getDomainInfo() {
		return domainInfo;
	}


	public void setDomainInfo(Map<String, String> domainIno) {
		domainInfo = domainIno;
	}


	public String getBasePackage() {
		return basePackage;
	}


	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}


	public List<IdFactory.IdInfo> getIdInfoList() {
		return idInfoList;
	}


	public void setIdInfoList(List<IdFactory.IdInfo> idInfoList) {
		this.idInfoList = idInfoList;
	}


	public IdFactory.IdInfo getDefaultIdInfo() {
		return defaultIdInfo;
	}


	public void setDefaultIdInfo(IdFactory.IdInfo defaultIdInfo) {
		this.defaultIdInfo = defaultIdInfo;
	}
}
