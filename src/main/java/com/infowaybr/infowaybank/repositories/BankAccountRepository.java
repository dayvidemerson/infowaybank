package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> { }
