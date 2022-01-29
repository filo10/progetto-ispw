package it.uniroma2.progettoispw.briscese.model;

import java.time.LocalDate;

public class License {
	private String code;
	private LocalDate expiration;

	public License(String code, LocalDate expiration) {
		this.code = code;
		this.expiration = expiration;
	}

	public String getCode() {
		return code;
	}

	public LocalDate getExpiration() {
		return expiration;
	}
}
