package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.LoginBean;
import it.uniroma2.progettoispw.briscese.bean.NotificationBean;
import it.uniroma2.progettoispw.briscese.dao.UserDAO;
import it.uniroma2.progettoispw.briscese.exceptions.*;
import it.uniroma2.progettoispw.briscese.extservice.UniversityDBBoundary;
import it.uniroma2.progettoispw.briscese.extservice.UniDBBean;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;
import it.uniroma2.progettoispw.briscese.model.roles.Passenger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

	public LoginBean login(LoginBean bean, NotificationBean notificationBean) throws MyLoginException {
		try {
			int userId = bean.getUserId();
			String password = bean.getPassword();

			User user = UserCatalog.getInstance().findUser(userId);

			String hash = hashPassword(password);
			String role = null;

			if (user.checkPassword(hash)) {
				if (user.hasRole("driver"))
					role = "driver";
				else if (user.hasRole("passenger"))
					role = "passenger";
				else if (user.hasRole("verifier"))
					role = "verifier";
				else if (user.hasRole("admin"))
					role = "admin";
				else
					throw new RoleException("there is a user with no roles!"); //something weird with roles
			} else {
				throw new WrongPasswordException();
			}

			user.subscribe(notificationBean);
			return new LoginBean(userId, user.getFullName(), role);

		} catch (UserNotFoundException e) {
			throw new MyLoginException("There is no User with ID=" + bean.getUserId());
		} catch (NoSuchAlgorithmException e) {
			throw new MyLoginException("There was an error encrypting the password... Please try again.\\nIf the error persists contact Client Service.\"");
		} catch (RoleException e) {
			throw new MyLoginException("The User has no role. Please contatct Client Service");
		} catch (WrongPasswordException e) {
			throw new MyLoginException("Wrong password, try again");
		}
	}

	public void signup(LoginBean bean) throws SignupException {
		/* NOTE this method creates only users with passenger role! */
		try {
			int userId = bean.getUserId();
			String fullName = bean.getFullName();
			String password = bean.getPassword();

			// check userID is not already used
			if (UserCatalog.getInstance().userExists(userId))
				throw new SignupException("There is already a User with id=" + userId);

			// check if userId is a valid enrollment number calling a secondary actor
			UniversityDBBoundary secondaryActorBoundary = new UniversityDBBoundary();
			UniDBBean uniDBBean = new UniDBBean(userId);
			uniDBBean = secondaryActorBoundary.isStudentEnrolled(uniDBBean);
			if (!uniDBBean.isEnrolled())
				throw new StudentDoesNotExistException(userId);

			// hash password
			String hashedPassword = hashPassword(password);

			// create user
			User newUser = UserCatalog.getInstance().newUser(userId, fullName, hashedPassword, new Passenger());
			UserDAO.getInstance().newUser(newUser);

		} catch (StudentDoesNotExistException e) {
			throw new SignupException("No Student is enrolled with this ID: " + bean.getUserId());
		} catch (NoSuchAlgorithmException e) {
			throw new SignupException("There was an error encrypting the password... Please try again.\nIf the error persists contact Client Service.");
		}
	}

	private String hashPassword(String passwordToHash) throws NoSuchAlgorithmException {
		String generatedPassword = null;

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

		return generatedPassword;
	}

	public void logout(LoginBean loginBean, NotificationBean notificationBean) throws MyLoginException {
		try {
			User user = UserCatalog.getInstance().findUser(loginBean.getUserId());
			user.unsubscribe(notificationBean);
		} catch (UserNotFoundException e) {
			throw new MyLoginException("Failed logout.\n"+e.getMessage());
		}
	}
}
