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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infowaybr.infowaybank.exceptions.BankNotFoundException;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.repositories.BankRepository;

@RestController
public class BankResource {

	@Autowired
	private BankRepository bankRepository;

	@GetMapping("/banks")
	public List<Bank> retrieveAll() {
		return bankRepository.findAll();
	}

	@GetMapping("/banks/{id}")
	public Bank retrieve(@PathVariable Long id) {
		Optional<Bank> bank = bankRepository.findById(id);

		if (!bank.isPresent())
			throw new BankNotFoundException();

		return bank.get();
	}

	@DeleteMapping("/banks/{id}")
	public void delete(@PathVariable long id) {
		bankRepository.deleteById(id);
	}

	@PostMapping("/banks")
	public ResponseEntity<Object> create(@RequestBody Bank bank) {
		Bank saved= bankRepository.save(bank);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/banks/{id}")
	public ResponseEntity<Object> update(@RequestBody Bank bank, @PathVariable long id) {

		Optional<Bank> bankOptional = bankRepository.findById(id);

		if (!bankOptional.isPresent())
			return ResponseEntity.notFound().build();

		bank.setId(id);
		
		bankRepository.save(bank);

		return ResponseEntity.noContent().build();
	}
}
