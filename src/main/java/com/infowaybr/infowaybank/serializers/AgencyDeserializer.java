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
import com.infowaybr.infowaybank.exceptions.BankNotFoundException;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;
import com.infowaybr.infowaybank.repositories.BankRepository;

public class AgencyDeserializer extends JsonDeserializer<Agency> {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public Agency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		Agency agency = new Agency();
		agency.setNumber(jsonNode.get("number").asInt());
		agency.setDigit(jsonNode.get("digit").asInt());
		agency.setCountry(jsonNode.get("country").asText());
		agency.setState(jsonNode.get("state").asText());
		agency.setCity(jsonNode.get("city").asText());
		agency.setDistrict(jsonNode.get("district").asText());
		agency.setStreet(jsonNode.get("street").asText());
		agency.setComplement(jsonNode.get("complement").asText());
		Long bankId = jsonNode.get("bank").asLong();
		Optional<Bank> bank = bankRepository.findById(bankId);
		
		if (bank.isPresent()) {			
			agency.setBank(bank.get());
		} else {
			throw new BankNotFoundException();
		}

		return agency;
	}

}
