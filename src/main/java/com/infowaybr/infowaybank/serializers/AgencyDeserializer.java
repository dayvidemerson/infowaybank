package com.infowaybr.infowaybank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infowaybr.infowaybank.models.Agency;
import com.infowaybr.infowaybank.models.Bank;

public class AgencyDeserializer extends JsonDeserializer<Agency> {

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
		Bank bank = new Bank();
		bank.setId(jsonNode.get("bank").asLong());
		agency.setBank(bank);
		return agency;
	}

}
