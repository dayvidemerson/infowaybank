package com.infowaybr.infowaybank.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class LoginRequest {

	@Positive
	@NotNull
	private Long bank;

	@Positive
	@NotNull
	private Long agency;

	@Positive
	@NotNull
	private Long agencyDigit;

	@Positive
	@NotNull
	private Long account;

	@Positive
	@NotNull
	private Long accountDigit;

	@NotBlank
	@NotNull
	private String password;

	public String getUsername() {
		String bank = this.bank.toString();
		String agency = this.agency.toString() + "-" + this.agencyDigit.toString();
		String account = this.account.toString() + "-" + this.accountDigit.toString();
		return bank + "-" + agency + "-" + account;
	}

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public Long getAgency() {
		return agency;
	}

	public void setAgency(Long agency) {
		this.agency = agency;
	}

	public Long getAgencyDigit() {
		return agencyDigit;
	}

	public void setAgencyDigit(Long agencyDigit) {
		this.agencyDigit = agencyDigit;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public Long getAccountDigit() {
		return accountDigit;
	}

	public void setAccountDigit(Long accountDigit) {
		this.accountDigit = accountDigit;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
