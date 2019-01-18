package com.infowaybr.infowaybank.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction implements Serializable {

	private static final long serialVersionUID = -4895481264680795583L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date created;

	@NotNull
	@NotBlank
	protected String description;

	@NotNull
	private Double value;

	@ManyToOne
	private BankAccount bankAccount;

	public Transaction() {
	}

	public Transaction(@NotNull @NotBlank String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
