package it.uniroma2.progettoispw.briscese.dao;

import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.model.License;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;
import it.uniroma2.progettoispw.briscese.model.roles.Admin;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;
import it.uniroma2.progettoispw.briscese.model.roles.Passenger;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private static UserDAO instance = null;

	private List<User> newUsers;
	private List<User> newDrivers;
	private static final String DRIVER = "driver";


	private UserDAO() {
		newUsers = new ArrayList<>();
		newDrivers = new ArrayList<>();
	}

	public static synchronized UserDAO getInstance() {
		if (UserDAO.instance == null)
			UserDAO.instance = new UserDAO();
		return instance;
	}

	public void retrieveUsersFromDB() throws DBConnectionException, SQLException, CannotAddRoleException {

		List<User> listOfUsers = new ArrayList<>();
		Statement stmt = DBConnectionManager.getInstance().getStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM users");

		while (rs.next()) { // for every record in the DB
			// create a User instance
			int userId = rs.getInt("enroll_number");
			User user = new User(userId, rs.getString("full_name"), rs.getString("password"));

			// assign roles
			switch (rs.getString("type")) {
				case DRIVER:
					user.addRole(new Passenger());
					License license = retrieveLicense(userId);
					Driver driver = new Driver(license);
					user.addRole(driver);
					break;
				case "passenger":
					user.addRole(new Passenger());
					break;
				case "admin":
					user.addRole(new Admin());
					break;
				case "verifier":
					user.addRole(new Verifier());
					break;
				default:
					throw new CannotAddRoleException("ERROR from DB retrieving Role for userID="+userId);
			}

			listOfUsers.add(user);
		}

		stmt.close(); // rs is also closed by this method

		// set the catalog
		UserCatalog.getInstance().setUsers(listOfUsers);
	}

	public void saveUsersintoDB() throws SQLException, DBConnectionException {
		Statement stmt = DBConnectionManager.getInstance().getStatement();

		// insert new users
		for (User newPassenger : newUsers) {
			String insertStatement = String.format("INSERT INTO users (enroll_number, full_name, password, type) VALUES (%s,'%s','%s','%s')",
					newPassenger.getUserId(), newPassenger.getFullName(), newPassenger.getPassword(), "passenger");
			stmt.executeUpdate(insertStatement);
		}

		// update passengers upgraded to drivers, save their licenses
		for (User newDriver : newDrivers) {
			int userId = newDriver.getUserId();
			String updateStatement = String.format("UPDATE users SET type='%s' WHERE enroll_number = %s",
					DRIVER, userId);
			stmt.executeUpdate(updateStatement);

			Driver role = (Driver) newDriver.getRoleInstance(DRIVER);
			License license = role.getLicense();
			String expiration = license.getExpiration().toString();
			String insertStatement = String.format("INSERT INTO license (code, expiration, owner) VALUES ('%s','%s',%s)",
					license.getCode(), expiration, userId);
			stmt.executeUpdate(insertStatement);
		}

		stmt.close();
	}

	private License retrieveLicense(int userID) throws DBConnectionException, SQLException, CannotAddRoleException {
		Statement stmt = DBConnectionManager.getInstance().getScrollableStatement();

		String sql = "SELECT * FROM license WHERE owner = " + userID + ";";
		ResultSet rs = stmt.executeQuery(sql);

		if (!rs.first()){ // rs empty
			String message = "No License found for the Driver with ID="+userID;
			throw new CannotAddRoleException(message);
		}

		rs.first();
		LocalDate expiration = LocalDate.parse(rs.getString("expiration"));
		License license = new License(rs.getString("code"), expiration);

		stmt.close();

		return license;
	}

	public void newUser(User user) {
		newUsers.add(user);
	}

	public void newDriver(User newDriver) {
		newDrivers.add(newDriver);
	}
}
