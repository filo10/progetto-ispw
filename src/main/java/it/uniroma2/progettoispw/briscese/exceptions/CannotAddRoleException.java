package it.uniroma2.progettoispw.briscese.exceptions;

public class CannotAddRoleException extends Exception {

	public CannotAddRoleException(int userId, String role) {
		super("Cannot add to User "+ userId + " this role:" + role);
	}

	public CannotAddRoleException(String message) {
		super(message);
	}

}
