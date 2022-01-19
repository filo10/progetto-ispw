package it.uniroma2.progettoispw.briscese.model.roles;

import it.uniroma2.progettoispw.briscese.model.roles.UserRole;

public class Verifier implements UserRole {

	@Override
	public boolean hasType(String value) {
		return value.equalsIgnoreCase("verifier");
	}
}
