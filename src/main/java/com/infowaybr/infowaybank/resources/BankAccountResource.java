package com.infowaybr.infowaybank.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infowaybr.infowaybank.exceptions.BankAccountNotFoundException;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping
	@ResponseStatus( HttpStatus.OK )
	public List<BankAccount> findAll() {
		return bankAccountRepository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	public BankAccount findById(@PathVariable Long id) {
		Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);

		if (!bankAccount.isPresent())
			throw new BankAccountNotFoundException();

		return bankAccount.get();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	public void delete(@PathVariable long id) {
		bankAccountRepository.deleteById(id);
	}

	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
	public BankAccount create(@Valid @RequestBody BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

	@PutMapping("/{id}")
	@ResponseStatus( HttpStatus.OK )
	public BankAccount update(@Valid @RequestBody BankAccount bankAccount, @PathVariable long id) {

		Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);

		if (!bankAccountOptional.isPresent())
			throw new BankAccountNotFoundException();

		bankAccount.setId(id);
		
		return bankAccountRepository.save(bankAccount);
	}
}
