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
import com.infowaybr.infowaybank.exceptions.BankAccountNotFoundException;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.models.Deposit;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;

public class DepositDeserializer extends JsonDeserializer<Deposit> {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public Deposit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		Deposit deposit = new Deposit();
		
		Double value = jsonNode.get("value").asDouble();
		deposit.setValue(value);
		Long bankAccountId = jsonNode.get("bankAccount").asLong();
		Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(bankAccountId);
		
		if (bankAccountOptional.isPresent()) {
			deposit.setBankAccount(bankAccountOptional.get());
		} else {
			throw new BankAccountNotFoundException();
		}

		return deposit;
	}
}
