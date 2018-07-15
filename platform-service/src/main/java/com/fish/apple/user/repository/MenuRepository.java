package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	
}
