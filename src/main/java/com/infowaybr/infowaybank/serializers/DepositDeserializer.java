package com.infowaybr.infowaybank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infowaybr.infowaybank.models.Deposit;

public class DepositDeserializer extends JsonDeserializer<Deposit> {

	@Override
	public Deposit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		Deposit deposit = new Deposit();
		
		Double value = jsonNode.get("value").asDouble();
		deposit.setValue(value);

		return deposit;
	}
}
