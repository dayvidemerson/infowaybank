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

import com.infowaybr.infowaybank.exceptions.ClientNotFoundException;
import com.infowaybr.infowaybank.models.Client;
import com.infowaybr.infowaybank.repositories.ClientRepository;

@RestController
@RequestMapping("/api/clients")
public class ClientResource {

	@Autowired
	private ClientRepository clientRepository;

	@GetMapping
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@GetMapping("/{id}")
	public Client findById(@PathVariable Long id) {
		Optional<Client> client = clientRepository.findById(id);

		if (!client.isPresent())
			throw new ClientNotFoundException();

		return client.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		clientRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Client client) {
		Client saved = clientRepository.save(client);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Client client, @PathVariable long id) {

		Optional<Client> clientOptional = clientRepository.findById(id);
		
		if (!clientOptional.isPresent())
			return ResponseEntity.notFound().build();

		client.setId(id);
		
		clientRepository.save(client);

		return ResponseEntity.noContent().build();
	}
}
