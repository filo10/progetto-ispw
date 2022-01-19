package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.model.roles.UserRole;

import java.util.List;

public class User {
	private int userId;
	private String fullName;
	private List<UserRole> roles;


	public boolean hasRole(String roleName) {
		for (UserRole role : roles) {
			if (role.hasType(roleName))
				return true;
		}
		return false;
	}

	public UserRole getRole(String roleName) {
		for (UserRole userRole : roles) {
			if (userRole.hasType(roleName))
				return userRole;
		}
		return null;
	}

	public void addRole(UserRole role) throws Exception {
		if (! canAddRole(role))
			throw new Exception(); //CannotAddRoleException
		roles.add(role);
	}

	private boolean canAddRole(UserRole value) {
		if (roles.isEmpty())	// if User has no roles
			return true;
		else if (this.hasRole("passenger") && !this.hasRole("driver") ) {            // if User is only a Passenger
			return value.hasType("driver");    // can be also a Driver
		}
		return false;
	}
}
