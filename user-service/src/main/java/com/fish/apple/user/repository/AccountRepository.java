package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.user.bo.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
