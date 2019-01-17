package com.infowaybr.infowaybank.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infowaybr.infowaybank.models.Withdrawal;
import com.infowaybr.infowaybank.repositories.WithdrawalRepository;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalResource {

	@Autowired
	private WithdrawalRepository withdrawalRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Withdrawal create(@Valid @RequestBody Withdrawal withdrawal) {
		withdrawal = withdrawalRepository.save(withdrawal);
		return withdrawal;
	}
}
