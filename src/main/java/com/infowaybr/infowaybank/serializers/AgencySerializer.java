package com.infowaybr.infowaybank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.infowaybr.infowaybank.models.Agency;

public class AgencySerializer extends JsonSerializer<Agency> {

	@Override
	public void serialize(Agency value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeNumberField("number", value.getNumber());
		gen.writeNumberField("digit", value.getDigit());
		gen.writeStringField("country", value.getCountry());
		gen.writeStringField("state", value.getState());
		gen.writeStringField("city", value.getCity());
		gen.writeStringField("district", value.getDistrict());
		gen.writeStringField("street", value.getStreet());
		gen.writeStringField("complement", value.getComplement());
		gen.writeNumberField("bank", value.getBank().getId());
		gen.writeEndObject();
	}

}
