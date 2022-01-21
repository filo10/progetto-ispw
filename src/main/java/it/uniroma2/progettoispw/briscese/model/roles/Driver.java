package it.uniroma2.progettoispw.briscese.model.roles;

import it.uniroma2.progettoispw.briscese.model.License;

public class Driver implements UserRole {
	private License license;


	public Driver(License license) {
		this.license = license;
	}

	@Override
	public boolean hasType(String value) {
		return value.equalsIgnoreCase("driver");
	}

}
