package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.user.bo.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	
}
