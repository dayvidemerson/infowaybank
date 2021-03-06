package com.infowaybr.infowaybank.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.OK)
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Bank findById(@PathVariable Long id) {
		Optional<Bank> bank = bankRepository.findById(id);

		if (!bank.isPresent())
			throw new BankNotFoundException();

		return bank.get();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable long id) {
		bankRepository.deleteById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Bank create(@Valid @RequestBody Bank bank) {
		return bankRepository.save(bank);
	}

	@CrossOrigin(origins="*")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Bank update(@Valid @RequestBody Bank bank, @PathVariable long id) {

		Optional<Bank> bankOptional = bankRepository.findById(id);

		if (!bankOptional.isPresent())
			throw new BankNotFoundException();

		bank.setId(id);

		return bankRepository.save(bank);
	}
}
