package it.uniroma2.progettoispw.briscese.model.roles;

public class Passenger implements UserRole {

	@Override
	public boolean hasType(String value) {
		return value.equalsIgnoreCase("passenger");
	}
}
