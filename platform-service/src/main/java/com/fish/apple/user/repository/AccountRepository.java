package com.fish.apple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fish.apple.platform.bo.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByAccountNo(String accountNo);

	Account findByAccountNoAndTenantNo(String currentTenantNo, String accountNo);
}
