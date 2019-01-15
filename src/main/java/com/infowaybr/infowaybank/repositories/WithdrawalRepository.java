package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> { }
