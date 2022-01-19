package it.uniroma2.progettoispw.briscese.model.roles;

public abstract interface UserRole {

	/**
	 * @param role This string is used for further computation in overriding classes
	 * for further details please read:
	 * 	https://www.martinfowler.com/apsupp/roles.pdf page 13 and following
	 */
	public abstract boolean hasType(String role);
}
