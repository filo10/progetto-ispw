package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.extservice.DummyUniversityDB;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;
import it.uniroma2.progettoispw.briscese.model.roles.Passenger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

	public String login(int userId, String password) throws Exception {
		User user = UserCatalog.getInstance().findUser(userId);
		if (user == null)
			throw new Exception(); //user not found
		String hash = hashPassword(password);
		if (user.checkPassword(hash)) {
			if (user.hasRole("driver"))
				return "driver";
			else if (user.hasRole("passenger"))
				return "passenger";
			else if (user.hasRole("verifier"))
				return "verifier";
			else if (user.hasRole("admin"))
				return "admin";
		}
		throw new Exception(); // wrong password or something weird with roles
	}

	public void signup(int userId, String fullName, String password) throws Exception {
		/* NOTE this method creates only users with passenger role! */

		// controlla che userid non sia già usato da un user
		if (UserCatalog.getInstance().findUser(userId) != null)
			throw new Exception(); //UserAlreadyExistsException();

		// controlla che userid è una matricola valida
		if (!DummyUniversityDB.getInstance().isEnrolled(userId))
			throw new Exception(); //StudentDoesNotExistException

		// hash password
		String hashedPassword = hashPassword(password);

		// crea user
		UserCatalog.getInstance().newUser(userId, fullName, hashedPassword, new Passenger());
	}

	private String hashPassword(String passwordToHash) {
		String generatedPassword = null;

		try
		{
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(passwordToHash.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedPassword;
	}
}
