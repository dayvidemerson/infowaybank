package com.infowaybr.infowaybank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Conta bancária não encontrada."));

		return CustomUserDetails.create(bankAccount);
	}

	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Conta bancária não encontrada."));

		return CustomUserDetails.create(bankAccount);
	}
}
