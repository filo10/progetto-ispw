package it.uniroma2.progettoispw.briscese.exceptions;

public class StudentDoesNotExistException extends Exception {

	public StudentDoesNotExistException() {
		super("Student does not exist");
	}

	public StudentDoesNotExistException(int userId) {
		super("There is no student enrolled with this id=" + userId);
	}
	public StudentDoesNotExistException(String message) {
		super("Student does not exist. Additional message: " + message);
	}
}
