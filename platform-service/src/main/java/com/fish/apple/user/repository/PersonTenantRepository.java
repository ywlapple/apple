package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.rel.PersonTenant;

public interface PersonTenantRepository extends JpaRepository<PersonTenant, Long> {

}
