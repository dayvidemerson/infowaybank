package com.infowaybr.infowaybank.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infowaybr.infowaybank.serializers.BankAccountDeserializer;
import com.infowaybr.infowaybank.serializers.BankAccountSerializer;

@Entity
@JsonSerialize(using = BankAccountSerializer.class)
@JsonDeserialize(using = BankAccountDeserializer.class)
public class BankAccount implements Serializable {

	private static final long serialVersionUID = 8725366328269761166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private String owner;

	@PositiveOrZero
	private Integer digit;

	@PositiveOrZero
	private Integer number;

	@NotNull
	@NotBlank
	private String password;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@OneToMany(mappedBy = "bankAccount")
	private Set<Transaction> transactions;

	public BankAccount() {
	}

	public BankAccount(@NotNull @NotBlank String owner, @PositiveOrZero Integer digit, @PositiveOrZero Integer number,
			@NotNull @NotBlank String password, Agency agency) {
		super();
		this.owner = owner;
		this.digit = digit;
		this.number = number;
		this.setPassword(password);
		this.agency = agency;
	}

	public Boolean checkPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		return password.equals(this.password);
	}

	public Double getBalance() {
		Double balance = 0.0;
		for (Transaction transaction : transactions) {
			balance += transaction.getValue();
		}
		return balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getDigit() {
		return digit;
	}

	public void setDigit(Integer digit) {
		this.digit = digit;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
}
