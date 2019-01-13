package com.infowaybr.infowaybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}
