package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.user.bo.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
