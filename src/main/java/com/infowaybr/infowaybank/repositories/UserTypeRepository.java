package com.infowaybr.infowaybank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infowaybr.infowaybank.models.User;
import com.infowaybr.infowaybank.models.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {

	List<UserType> findByUserIn(User user);
}
