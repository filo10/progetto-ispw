package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.model.roles.Passenger;

import java.util.ArrayList;
import java.util.List;

public class UserCatalog {

	private static UserCatalog instance = null;

	private List<User> users;


	protected UserCatalog() {
		this.users = new ArrayList<>();
	}

	public User findUser(int id) {
		for (User u : users) {
			if (u.getUserId() == id)
				return u;
		}
		return null;
	}

	public static synchronized UserCatalog getInstance() {
		if (UserCatalog.instance == null)
			UserCatalog.instance = new UserCatalog();
		return instance;
	}

	public void newUser(int userId, String fullname, String password, Passenger passenger) {
		User newUser = new User(userId, fullname, password, passenger);
		users.add(newUser);
	}
}
