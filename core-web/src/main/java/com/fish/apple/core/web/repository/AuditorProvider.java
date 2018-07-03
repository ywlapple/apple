package com.fish.apple.core.web.repository;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.fish.apple.core.web.env.Environment;

@Component
public class AuditorProvider implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		System.out.println("fdas");
		return Optional.ofNullable(Environment.currentUserNo());
	}

}
