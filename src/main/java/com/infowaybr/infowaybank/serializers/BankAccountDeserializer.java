package com.infowaybr.infowaybank.serializers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infowaybr.infowaybank.exceptions.AgencyNotFoundException;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.repositories.AgencyRepository;

public class BankAccountDeserializer extends JsonDeserializer<BankAccount> {

	@Autowired
	private AgencyRepository agencyRepository;

	@Override
	public BankAccount deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		BankAccount bankAccount = new BankAccount();
		bankAccount.setOwner(jsonNode.get("owner").asText());
		bankAccount.setNumber(jsonNode.get("number").asInt());
		bankAccount.setDigit(jsonNode.get("digit").asInt());
		bankAccount.setPassword(jsonNode.get("password").asText());

		Long agencyId = jsonNode.get("agency").asLong();
		Optional<Agency> agency = agencyRepository.findById(agencyId);
		if (agency.isPresent()) {
			bankAccount.setAgency(agency.get());
		} else {
			throw new AgencyNotFoundException();
		}
		return bankAccount;
	}
}
