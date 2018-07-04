package com.fish.apple.core.web.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

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
	private Map<String, String> bidInfo;
	/**
	 * key: class name
	 * value: business property name
	 */
	private Map<String, String> domainInfo;
	
	private String basePackage;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO , 扫描basePackage 路径下的 entity，根据@BId获取相关信息，如果配置中已经配置，则不做覆盖
		System.out.println("bidProperties 配置完成");
//		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
//		provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
//		final Set<BeanDefinition> beans = provider.findCandidateComponents(basePackage);
//		
//		for(BeanDefinition definition : beans ) {
//			try {
//				Class<?> clazz = Class.forName(definition.getBeanClassName());
//				
//			}
//			
//		}
		
	}
	
	private ThreadPoolTaskExecutor sequenceThreadPoolTaskExecutor;
	
	private List<IdFactory.IdInfo> idInfoList ;
	
	private IdFactory.IdInfo defaultIdInfo ;
	
	public BIdProperties() {
		IdInfo idInfo = new IdFactory.IdInfo();
		idInfo.setName("common");
		idInfo.setSequenceName("common");
		idInfo.setPattern("yyyyMMdd0000"); // date日期的格式+ 0 ，0表示要替换成数字序列
		idInfo.setPrefix("BI"); //prefix
		this.defaultIdInfo = idInfo;
	}
	
	
}
