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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.fish.apple.core.common.constant.Constant;
import com.fish.apple.core.common.domain.BId;
import com.fish.apple.core.web.repository.IdFactory.IdInfo;

import lombok.Data;

@ConfigurationProperties(prefix = BIdProperties.ENTITY_PREFIX)
@Component
@Data
public class BIdProperties implements InitializingBean{

	public static final String ENTITY_PREFIX = "app.entity";
	
	/**
	 * key: class name
	 * value: sequence name
	 */
	private Map<String, String> bidInfo = new HashMap<>();
	/**
	 * key: class name
	 * value: business property name
	 */
	private Map<String, String> domainInfo = new HashMap<>();
	
	private String basePackage = Constant.DomainBasePackage.getCode();
	
	

	
	private ThreadPoolTaskExecutor sequenceThreadPoolTaskExecutor;
	
	private List<IdFactory.IdInfo> idInfoList ;
	
	private IdFactory.IdInfo defaultIdInfo ;
	
	private static BIdProperties bidProperties;
	
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
		bidProperties = this;
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
					bidInfo.putIfAbsent(clazz.getName(), idName);
				}
			}
		}
		Class superclass = clazz.getSuperclass();
		findFieldByAnnotion(superclass );
	}
	
	
	public static Map<String, String> getDomain(){
		return bidProperties.getDomainInfo();
	}
	public static Map<String, String> getBId(){
		return bidProperties.getBidInfo();
	}
}
