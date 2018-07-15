package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
