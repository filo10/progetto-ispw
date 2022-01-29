package it.uniroma2.progettoispw.briscese.model.roles;

public interface UserRole {

	/*
	 * for further details please read:
	 * 	https://www.martinfowler.com/apsupp/roles.pdf page 13 and following
	 */
	boolean hasType(String role);
}
