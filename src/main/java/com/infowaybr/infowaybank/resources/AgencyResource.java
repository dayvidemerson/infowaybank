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

import com.infowaybr.infowaybank.exceptions.AgencyNotFoundException;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.repositories.AgencyRepository;

@RestController
@RequestMapping("/api/agencies")
public class AgencyResource {

	@Autowired
	private AgencyRepository agencyRepository;

	@GetMapping
	public List<Agency> findAll() {
		return agencyRepository.findAll();
	}

	@GetMapping("/{id}")
	public Agency findById(@PathVariable Long id) {
		Optional<Agency> agency = agencyRepository.findById(id);

		if (!agency.isPresent())
			throw new AgencyNotFoundException();

		return agency.get();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		agencyRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Agency agency) {
		Agency saved = agencyRepository.save(agency);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Agency agency, @PathVariable long id) {

		Optional<Agency> agencyOptional = agencyRepository.findById(id);

		if (!agencyOptional.isPresent())
			return ResponseEntity.notFound().build();

		agency.setId(id);
		
		agencyRepository.save(agency);

		return ResponseEntity.noContent().build();
	}
}
