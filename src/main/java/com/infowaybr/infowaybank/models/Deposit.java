package com.infowaybr.infowaybank.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infowaybr.infowaybank.serializers.DepositDeserializer;
import com.infowaybr.infowaybank.serializers.DepositSerializer;

@Entity
@JsonSerialize(using = DepositSerializer.class)
@JsonDeserialize(using = DepositDeserializer.class)
public class Deposit extends Transaction {

	private static final long serialVersionUID = 5318493360983251704L;

	public Deposit() {
		this.description = "Deposito";
	}

	public Deposit(@NotNull Double value, BankAccount bankAccount) {
		super();
		this.setValue(value);
		this.setBankAccount(bankAccount);
	}

	@Override
	public void setValue(Double value) {
		if (value < 0) {
			value *= -1;
		}
		super.setValue(value);
	}
}
