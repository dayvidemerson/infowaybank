package com.infowaybr.infowaybank.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Positive;

public class Transfer implements Serializable {

	private static final long serialVersionUID = -727436324842220427L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Positive
	private Double value;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@ManyToOne
	private BankAccount from;

	@ManyToOne
	private BankAccount to;

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

	public BankAccount getFrom() {
		return from;
	}

	public void setFrom(BankAccount from) {
		this.from = from;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public BankAccount getTo() {
		return to;
	}

	public void setTo(BankAccount to) {
		this.to = to;
	}
}
