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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infowaybr.infowaybank.exceptions.AgencyNotFoundException;
import com.infowaybr.infowaybank.exceptions.BankNotFoundException;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.repositories.AgencyRepository;
import com.infowaybr.infowaybank.repositories.BankRepository;

@RestController
@RequestMapping("/api/agencies")
public class AgencyResource {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Agency> findAll(@RequestParam(name = "bank", required = false) Long bankId) {
		if (bankId != null) {
			Optional<Bank> bankOptional = bankRepository.findById(bankId);
			if (bankOptional.isPresent()) {
				return bankOptional.get().getAgencies();
			} else {
				throw new BankNotFoundException();
			}
		}
		return agencyRepository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Agency findById(@PathVariable Long id) {
		Optional<Agency> agency = agencyRepository.findById(id);

		if (!agency.isPresent())
			throw new AgencyNotFoundException();

		return agency.get();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable long id) {
		agencyRepository.deleteById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Agency create(@Valid @RequestBody Agency agency) {
		checkAgencyExists(agency);
		return agencyRepository.save(agency);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Agency update(@Valid @RequestBody Agency agency, @PathVariable long id) {

		Optional<Agency> agencyOptional = agencyRepository.findById(id);

		if (!agencyOptional.isPresent())
			throw new AgencyNotFoundException();

		agency.setId(id);

		if (!agency.getCode().equals(agencyOptional.get().getCode())) {
			checkAgencyExists(agency);
		}

		return agencyRepository.save(agency);
	}

	private void checkAgencyExists(Agency agency) {
		Bank bank = agency.getBank();
		for (Agency agencyCheck : bank.getAgencies()) {
			if (agency.getCode().equals(agencyCheck.getCode())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número indisponível para registro de agência.");
			}
		}
	}
}
