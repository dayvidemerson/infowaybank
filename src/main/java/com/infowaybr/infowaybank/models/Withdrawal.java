package com.infowaybr.infowaybank.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.infowaybr.infowaybank.serializers.WithdrawalDeserializer;

@Entity
@JsonDeserialize(using = WithdrawalDeserializer.class)
public class Withdrawal extends Transaction {

	private static final long serialVersionUID = -1756813413552936219L;

	public Withdrawal() {
		this.description = "Saque";
	}

	public Withdrawal(@NotNull Double value, BankAccount bankAccount) {
		super();
		this.setValue(value);
		this.setBankAccount(bankAccount);
	}

	@Override
	public void setValue(Double value) {
		if (value > 0) {
			value *= -1;
		}
		super.setValue(value);
	}
}
