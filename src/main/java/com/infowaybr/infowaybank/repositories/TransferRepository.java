package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> { }
