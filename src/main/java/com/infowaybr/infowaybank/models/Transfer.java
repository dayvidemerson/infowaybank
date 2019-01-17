package com.infowaybr.infowaybank.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Transfer implements Serializable {

	private static final long serialVersionUID = -727436324842220427L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date created;

	@OneToOne
	private Transaction from;

	@OneToOne
	private Transaction to;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Transaction getFrom() {
		return from;
	}

	public void setFrom(Transaction from) {
		this.from = from;
	}

	public Transaction getTo() {
		return to;
	}

	public void setTo(Transaction to) {
		this.to = to;
	}
	
	public Date getCreated() {
		return created;
	}
}
