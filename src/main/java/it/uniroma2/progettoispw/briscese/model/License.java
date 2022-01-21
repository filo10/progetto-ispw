package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.model.roles.Driver;

import java.time.LocalDate;

public class License {
	private Driver owner;
	private String code;
	private LocalDate expiration;

	public License(String code, LocalDate expiration) {
		this.code = code;
		this.expiration = expiration;
	}

	public void setOwner(Driver owner) {
		this.owner = owner;
	}

	public String getCode() {
		return code;
	}

	public LocalDate getExpiration() {
		return expiration;
	}
}
