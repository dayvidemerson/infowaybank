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

import com.infowaybr.infowaybank.exceptions.TransferNotFoundException;
import com.infowaybr.infowaybank.models.Transfer;
import com.infowaybr.infowaybank.repositories.TransferRepository;

@RestController
@RequestMapping("/api/transfers")
public class TransferResource {

	@Autowired
	private TransferRepository transferRepository;

	@GetMapping
	public List<Transfer> findAll() {
		return transferRepository.findAll();
	}

	@GetMapping("/{id}")
	public Transfer findById(@PathVariable Long id) {
		Optional<Transfer> transfer = transferRepository.findById(id);

		if (!transfer.isPresent())
			throw new TransferNotFoundException();

		return transfer.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		transferRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Transfer transfer) {
		Transfer saved = transferRepository.save(transfer);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Transfer transfer, @PathVariable long id) {

		Optional<Transfer> transferOptional = transferRepository.findById(id);
		
		if (!transferOptional.isPresent())
			return ResponseEntity.notFound().build();

		transfer.setId(id);
		
		transferRepository.save(transfer);

		return ResponseEntity.noContent().build();
	}
}
