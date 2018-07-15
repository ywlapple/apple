package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.rel.PersonTenant;

public interface OrgRepository extends JpaRepository<PersonTenant, Long> {

}
