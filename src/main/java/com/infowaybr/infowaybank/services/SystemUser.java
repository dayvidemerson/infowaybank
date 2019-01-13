package com.infowaybr.infowaybank.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = 2836054699577384161L;

	private String nome;

	public SystemUser(String nome, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
