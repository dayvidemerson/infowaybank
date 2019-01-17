package com.infowaybr.infowaybank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	public List<Transaction> findByBankAccountOrderByCreatedDesc(BankAccount bankAccount);

}
