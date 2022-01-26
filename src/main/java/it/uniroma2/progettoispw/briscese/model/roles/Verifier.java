package it.uniroma2.progettoispw.briscese.model.roles;


public class Verifier implements UserRole {

	@Override
	public boolean hasType(String value) {
		return value.equalsIgnoreCase("verifier");
	}
}
