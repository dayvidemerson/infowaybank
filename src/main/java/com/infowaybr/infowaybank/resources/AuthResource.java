package com.infowaybr.infowaybank.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infowaybr.infowaybank.payloads.JwtAuthenticationResponse;
import com.infowaybr.infowaybank.payloads.LoginRequest;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;
import com.infowaybr.infowaybank.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("/token")
	public ResponseEntity<?> authenticateBankAccount(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
}
