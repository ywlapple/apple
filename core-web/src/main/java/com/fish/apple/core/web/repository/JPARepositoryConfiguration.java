package com.fish.apple.core.web.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages="com.fish.apple.core.web.repository")
public class JPARepositoryConfiguration {
	
	@ConditionalOnMissingBean(SequenceRepository.class)
	@Bean
	public SequenceRepository securityEvaluationContextExtension() {
		return new SequenceRepository() {
			
			private long count = System.currentTimeMillis();
			
			@Override
			public Long next(String sequenceName) {
				
				return count++;
			}
			
			@Override
			public void flush(String sequenceName) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
