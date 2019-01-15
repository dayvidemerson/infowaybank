package com.infowaybr.infowaybank.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.infowaybr.infowaybank.exceptions.BankNotFoundException;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.repositories.BankRepository;

@RestController
@RequestMapping("/api/banks")
public class BankResource {

	@Autowired
	private BankRepository bankRepository;

	@GetMapping
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	@GetMapping("/{id}")
	public Bank findById(@PathVariable Long id) {
		Optional<Bank> bank = bankRepository.findById(id);

		if (!bank.isPresent())
			throw new BankNotFoundException();

		return bank.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		bankRepository.deleteById(id);
	}

	@PostMapping
	public Bank create(@Valid @RequestBody Bank bank) {
		return bankRepository.save(bank);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Bank bank, @PathVariable long id) {

		Optional<Bank> bankOptional = bankRepository.findById(id);

		if (!bankOptional.isPresent())
			return ResponseEntity.notFound().build();

		bank.setId(id);
		
		bankRepository.save(bank);

		return ResponseEntity.noContent().build();
	}
}
