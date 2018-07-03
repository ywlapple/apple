package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.user.bo.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
