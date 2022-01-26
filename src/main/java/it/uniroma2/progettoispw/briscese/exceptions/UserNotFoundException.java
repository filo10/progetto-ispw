package it.uniroma2.progettoispw.briscese.exceptions;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("No user was found searching by userID");
	}

	public UserNotFoundException(int userId) {
		super("There is no User with userId=" + userId);
	}

	public UserNotFoundException (String message){
		super("No user was found searching by userID. Additional message: " + message);
	}

	public UserNotFoundException (Throwable cause) {
		super(cause);
	}

	public UserNotFoundException (String message, Throwable cause) {
		super("No user was found searching by userID. Additional message: " + message, cause);
	}
}
