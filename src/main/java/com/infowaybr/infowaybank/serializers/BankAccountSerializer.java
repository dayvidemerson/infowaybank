package com.infowaybr.infowaybank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.infowaybr.infowaybank.models.BankAccount;

public class BankAccountSerializer extends JsonSerializer<BankAccount> {

	@Override
	public void serialize(BankAccount value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		
		if (value.getId() != null) {			
			gen.writeNumberField("id", value.getId());
		}

		gen.writeStringField("owner", value.getOwner());
		gen.writeNumberField("digit", value.getDigit());
		gen.writeNumberField("number", value.getNumber());
		gen.writeStringField("password", value.getPassword());
		gen.writeNumberField("agency", value.getAgency().getId());
		gen.writeNumberField("balance", value.getBalance());
		gen.writeEndObject();
	}
}
