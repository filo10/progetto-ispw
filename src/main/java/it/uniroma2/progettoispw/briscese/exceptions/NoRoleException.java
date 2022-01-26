package it.uniroma2.progettoispw.briscese.exceptions;

public class NoRoleException extends Exception {
	public NoRoleException() {
		super("there is a user with no roles!");
	}
}
