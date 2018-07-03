package com.fish.apple.core.web.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fish.apple.core.web.env.User;

@Configuration
public class SequenceConfig {
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
