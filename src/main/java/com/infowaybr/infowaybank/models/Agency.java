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

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infowaybr.infowaybank.serializers.AgencyDeserializer;
import com.infowaybr.infowaybank.serializers.AgencySerializer;

@Entity
@JsonSerialize(using = AgencySerializer.class)
@JsonDeserialize(using = AgencyDeserializer.class)
public class Agency implements Serializable {

	private static final long serialVersionUID = 7782384712441871304L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@PositiveOrZero
	private Integer digit;

	@NotNull
	@PositiveOrZero
	private Integer number;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bank_id")
	private Bank bank;

	@OneToMany(mappedBy = "agency")
	private Set<BankAccount> bankAccounts;

	@NotBlank
	private String country;

	@Length(min = 2, max = 2)
	private String state;

	@NotBlank
	private String city;

	@NotBlank
	private String district;

	@NotBlank
	private String street;

	private String complement;

	public Agency() {
	}

	public Agency(@NotNull @PositiveOrZero Integer digit, @NotNull @PositiveOrZero Integer number, Bank bank,
			@NotBlank String country, @Length(min = 2, max = 2) String state, @NotBlank String city,
			@NotBlank String district, @NotBlank String street) {
		super();
		this.digit = digit;
		this.number = number;
		this.bank = bank;
		this.country = country;
		this.state = state;
		this.city = city;
		this.district = district;
		this.street = street;
	}

	public String getCode() {
		return number.toString() + "-" + digit.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public Set<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Set<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
}
