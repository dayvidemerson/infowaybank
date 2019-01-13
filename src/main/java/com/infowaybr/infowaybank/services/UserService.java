package com.infowaybr.infowaybank.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.infowaybr.infowaybank.models.User;
import com.infowaybr.infowaybank.models.UserType;
import com.infowaybr.infowaybank.repositories.UserRepository;
import com.infowaybr.infowaybank.repositories.UserTypeRepository;

public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}

		return new SystemUser(user.getName(), user.getUsername(), user.getPassword(), authorities(user));
	}

	public Collection<? extends GrantedAuthority> authorities(User user) {
		return authorities(userTypeRepository.findByUserIn(user));
	}

	private Collection<? extends GrantedAuthority> authorities(List<UserType> userTypes) {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		for (UserType userType: userTypes) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userType.getName()));
		}
		
		return grantedAuthorities;
	}

}
