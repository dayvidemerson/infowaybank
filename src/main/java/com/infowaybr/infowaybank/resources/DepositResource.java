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

import com.infowaybr.infowaybank.exceptions.DepositNotFoundException;
import com.infowaybr.infowaybank.models.Deposit;
import com.infowaybr.infowaybank.repositories.DepositRepository;

@RestController
@RequestMapping("/api/deposits")
public class DepositResource {

	@Autowired
	private DepositRepository depositRepository;

	@GetMapping
	public List<Deposit> findAll() {
		return depositRepository.findAll();
	}

	@GetMapping("/{id}")
	public Deposit findById(@PathVariable Long id) {
		Optional<Deposit> deposit = depositRepository.findById(id);

		if (!deposit.isPresent())
			throw new DepositNotFoundException();

		return deposit.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		depositRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Deposit deposit) {
		Deposit saved = depositRepository.save(deposit);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Deposit deposit, @PathVariable long id) {

		Optional<Deposit> depositOptional = depositRepository.findById(id);
		
		if (!depositOptional.isPresent())
			return ResponseEntity.notFound().build();

		deposit.setId(id);
		
		depositRepository.save(deposit);

		return ResponseEntity.noContent().build();
	}
}
