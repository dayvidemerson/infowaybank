package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> { }
