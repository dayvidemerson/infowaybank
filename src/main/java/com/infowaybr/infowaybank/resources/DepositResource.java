package com.infowaybr.infowaybank.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infowaybr.infowaybank.models.Deposit;
import com.infowaybr.infowaybank.repositories.DepositRepository;

@RestController
@RequestMapping("/api/deposits")
public class DepositResource {

	@Autowired
	private DepositRepository depositRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Deposit create(@Valid @RequestBody Deposit deposit) {
		deposit = depositRepository.save(deposit);
		return deposit;
	}
}
