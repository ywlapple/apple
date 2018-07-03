package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.user.bo.Org;

public interface OrgRepository extends JpaRepository<Org, Long> {

}
