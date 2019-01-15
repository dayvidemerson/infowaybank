package com.infowaybr.infowaybank.resources;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infowaybr.infowaybank.exceptions.WithdrawalNotFoundException;
import com.infowaybr.infowaybank.models.Withdrawal;
import com.infowaybr.infowaybank.repositories.WithdrawalRepository;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalResource {

	@Autowired
	private WithdrawalRepository withdrawalRepository;

	@GetMapping
	public List<Withdrawal> findAll() {
		return withdrawalRepository.findAll();
	}

	@GetMapping("/{id}")
	public Withdrawal findById(@PathVariable Long id) {
		Optional<Withdrawal> withdrawal = withdrawalRepository.findById(id);

		if (!withdrawal.isPresent())
			throw new WithdrawalNotFoundException();

		return withdrawal.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		withdrawalRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Withdrawal withdrawal) {
		Withdrawal saved = withdrawalRepository.save(withdrawal);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Withdrawal withdrawal, @PathVariable long id) {

		Optional<Withdrawal> withdrawalOptional = withdrawalRepository.findById(id);
		
		if (!withdrawalOptional.isPresent())
			return ResponseEntity.notFound().build();

		withdrawal.setId(id);
		
		withdrawalRepository.save(withdrawal);

		return ResponseEntity.noContent().build();
	}
}
