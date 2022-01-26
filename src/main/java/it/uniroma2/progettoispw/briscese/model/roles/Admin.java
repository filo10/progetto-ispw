package it.uniroma2.progettoispw.briscese.model.roles;

public class Admin implements UserRole {

	@Override
	public boolean hasType(String role) {
		return role.equalsIgnoreCase("admin");
	}
}
