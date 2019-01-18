package com.infowaybr.infowaybank.serializers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.infowaybr.infowaybank.models.Deposit;

public class DepositSerializer extends JsonSerializer<Deposit> {
	
	@Override
	public void serialize(Deposit value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		
		if (value.getId() != null) {			
			gen.writeNumberField("id", value.getId());
		}

		gen.writeNumberField("value", value.getValue());
		gen.writeStringField("description", value.getDescription());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String created = dateFormat.format(value.getCreated());
		gen.writeStringField("created", created);

		gen.writeEndObject();
	}

}
