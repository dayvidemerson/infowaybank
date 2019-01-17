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
import com.infowaybr.infowaybank.exceptions.InsufficientBalanceException;
import com.infowaybr.infowaybank.models.BankAccount;
import com.infowaybr.infowaybank.models.Withdrawal;
import com.infowaybr.infowaybank.repositories.BankAccountRepository;

public class WithdrawalDeserializer extends JsonDeserializer<Withdrawal> {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public Withdrawal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec objectCodec = p.getCodec();
		JsonNode jsonNode = objectCodec.readTree(p);

		Withdrawal withdrawal = new Withdrawal();
		
		Double value = jsonNode.get("value").asDouble();
		withdrawal.setValue(value);
		Long bankAccountId = jsonNode.get("bankAccount").asLong();
		Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(bankAccountId);
		
		if (bankAccountOptional.isPresent()) {
			BankAccount bankAccount = bankAccountOptional.get();

			value = withdrawal.getValue() * -1;

			if (bankAccount.getBalance() < value) {
				throw new InsufficientBalanceException("Saldo insuficiente");
			}

			withdrawal.setBankAccount(bankAccount);
		} else {
			throw new BankAccountNotFoundException();
		}

		return withdrawal;
	}
}
