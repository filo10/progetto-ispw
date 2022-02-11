package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.bean.NotificationBean;
import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.model.roles.Passenger;
import it.uniroma2.progettoispw.briscese.model.roles.UserRole;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
	private String fullName;
	private String password;
	private List<UserRole> roles = new ArrayList<>();
	private List<String> notificationBacklog = new ArrayList<>();
	private static final String DRIVER = "driver";

	public User(int userId, String fullName, String password, Passenger passenger) {
		this.userId = userId;
		this.fullName = fullName;
		this.password = password;
		this.roles.add(passenger);
	}

	public User(int userId, String fullName, String password) {
		this.userId = userId;
		this.fullName = fullName;
		this.password = password;
	}

	public boolean hasRole(String roleName) {
		for (UserRole role : roles) {
			if (role.hasType(roleName))
				return true;
		}
		return false;
	}

	public UserRole getRoleInstance(String roleName) {
		for (UserRole userRole : roles) {
			if (userRole.hasType(roleName))
				return userRole;
		}
		return null;
	}

	public void addRole(UserRole role) throws CannotAddRoleException {
		if (! canAddRole(role))
			throw new CannotAddRoleException(userId, role.toString());
		roles.add(role);
	}

	private boolean canAddRole(UserRole value) {
		if (roles.isEmpty()) {    // if User has no roles
			return !value.hasType(DRIVER);
		}
		else if (this.hasRole("passenger") && !this.hasRole(DRIVER) ) { // if User is only a Passenger
			return value.hasType(DRIVER);    // can be also a Driver
		}
		return false;
	}

	public int getUserId() {
		return userId;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPassword() {
		return password;
	}

	public boolean checkPassword(String hash) {
		return this.password.equals(hash);
	}

// publisher ___________________________________________________________________________________________________________
	// per inoltrare notifiche alle view registrate qui, attraverso un bean

	private List<NotificationBean> subscribers = new ArrayList<>();

	public void subscribe(NotificationBean bean) {
		subscribers.add(bean);
		for (String notification : notificationBacklog)
			publishNotification(notification);
	}

	public void unsubscribe(NotificationBean bean) {
		subscribers.remove(bean);
	}

	public void publishNotification(String message) {
		/* remember notification if user is not online */
		if (subscribers.isEmpty()) {
			notificationBacklog.add(message);
			return;
		}

		for (NotificationBean bean : subscribers)
			bean.alert(message);
	}
//______________________________________________________________________________________________________________________

}
