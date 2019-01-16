package com.infowaybr.infowaybank.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Bank implements Serializable {

	private static final long serialVersionUID = 7745206886300687410L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private String name;

	@OneToMany(mappedBy = "bank")
	private Set<Agency> agencies;

	public Bank() {
	}

	public Bank(@NotNull @NotBlank String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Agency> getAgencies() {
		return agencies;
	}

	public void setAgencies(Set<Agency> agencies) {
		this.agencies = agencies;
	}
}
