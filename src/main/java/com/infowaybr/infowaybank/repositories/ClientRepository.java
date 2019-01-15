package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> { }
