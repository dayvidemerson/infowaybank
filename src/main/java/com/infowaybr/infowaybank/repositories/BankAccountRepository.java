package com.infowaybr.infowaybank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	
	public Optional<BankAccount> findByUsername(String username);

}
