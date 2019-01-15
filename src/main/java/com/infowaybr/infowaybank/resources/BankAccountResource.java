package com.infowaybr.infowaybank.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infowaybr.infowaybank.exceptions.BankAccountNotFoundException;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountResource {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping
	public List<BankAccount> findAll() {
		return bankAccountRepository.findAll();
	}

	@GetMapping("/{id}")
	public BankAccount findById(@PathVariable Long id) {
		Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);

		if (!bankAccount.isPresent())
			throw new BankAccountNotFoundException();

		return bankAccount.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		bankAccountRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody BankAccount bankAccount) {
		BankAccount saved = bankAccountRepository.save(bankAccount);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody BankAccount bankAccount, @PathVariable long id) {

		Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);

		if (!bankAccountOptional.isPresent())
			return ResponseEntity.notFound().build();

		bankAccount.setId(id);
		
		bankAccountRepository.save(bankAccount);

		return ResponseEntity.noContent().build();
	}
}
