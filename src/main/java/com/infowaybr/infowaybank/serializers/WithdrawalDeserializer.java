package com.infowaybr.infowaybank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.infowaybr.infowaybank.models.Withdrawal;

public class WithdrawalDeserializer extends JsonDeserializer<Withdrawal> {

	@Override
	public Withdrawal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		Withdrawal withdrawal = new Withdrawal();
		
		Double value = jsonNode.get("value").asDouble();
		withdrawal.setValue(value);

		return withdrawal;
	}
}
