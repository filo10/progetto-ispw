package it.uniroma2.progettoispw.briscese.dao;

import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.model.Ride;
import it.uniroma2.progettoispw.briscese.model.RideCatalog;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
	private static RideDAO instance = null;
	private List<Ride> newRides;
	private List<Ride> deletedRides;


	private RideDAO() {
		newRides = new ArrayList<>();
		deletedRides = new ArrayList<>();
	}

	public static synchronized RideDAO getInstance() {
		if (RideDAO.instance == null)
			RideDAO.instance = new RideDAO();
		return instance;
	}

	public void retrieveRides() throws DBConnectionException, SQLException, UserNotFoundException, RoleException {
		List<Ride> list = new ArrayList<>();

		Statement stmt = DBConnectionManager.getInstance().getStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM rides");

		while (rs.next()) {
			// sql Ride(id, date, time, start, destination, driver, number_of_seats)
			// java Ride: int rideID, User driver, int numSeats, list passengerlist, requestlist, String startpoint finishpoint, date, time

			int rideId = rs.getInt("id");
			User driver = UserCatalog.getInstance().findUser(rs.getInt("driver"));
			int numberOfSeats = rs.getInt("number_of_seats");
			String start = rs.getString("start");
			String finish = rs.getString("destination");
			LocalDate date = LocalDate.parse(rs.getString("date"));
			LocalTime time = LocalTime.parse(rs.getString("time"));

			Ride ride = new Ride(rideId, driver, numberOfSeats, start, finish, date, time);
			list.add(ride);

			retrievePassengers(ride);
			retrieveRequests(ride);
		}

		rs = stmt.executeQuery("SELECT max(id) AS maxId FROM rides");
		rs.next();
		int nextId = rs.getInt("maxId") + 1;

		stmt.close();

		RideCatalog.getInstance().setNextId(nextId);
		RideCatalog.getInstance().setRideList(list);
	}

	private void retrievePassengers(Ride ride) throws DBConnectionException, SQLException, UserNotFoundException {
		List<User> list = new ArrayList<>();
		Statement stmt = DBConnectionManager.getInstance().getStatement();
		ResultSet rs = stmt.executeQuery("SELECT passenger FROM seat WHERE ride = " + ride.getRideId());
		while (rs.next()) {
			User passenger = UserCatalog.getInstance().findUser(rs.getInt("passenger"));
			list.add(passenger);
		}
		ride.setPassengerList(list);
	}

	private void retrieveRequests(Ride ride) throws DBConnectionException, SQLException, UserNotFoundException {
		List<User> list = new ArrayList<>();
		Statement stmt = DBConnectionManager.getInstance().getStatement();
		ResultSet rs = stmt.executeQuery("SELECT passenger FROM seat_request WHERE ride = " + ride.getRideId());
		while (rs.next()) {
			User passenger = UserCatalog.getInstance().findUser(rs.getInt("passenger"));
			list.add(passenger);
		}
		ride.setRequestList(list);
	}

	public void saveRides() throws DBConnectionException, SQLException {
		Statement statement = DBConnectionManager.getInstance().getStatement();

		// delete from DB rides deleted in this execution. passengers and requests will be deleted with sql CASCADE.
		for (Ride deletedRide : deletedRides) {
			statement.executeUpdate("DELETE FROM rides WHERE id = " + deletedRide.getRideId());
		}

		// save rides created in this execution
		for (Ride newRide : newRides) {
			String sql = String.format("INSERT INTO rides (id, date, time, start, destination, driver, number_of_seats) VALUES (%s, '%s', '%s', '%s', '%s', %s, %s)",
					newRide.getRideId(),
					newRide.getDate().toString(),
					newRide.getTime().toString(),
					newRide.getStartPoint(),
					newRide.getFinishPoint(),
					newRide.getDriver().getUserId(),
					newRide.getNumberOfSeats());
			statement.executeUpdate(sql);
		}

		statement.executeUpdate("TRUNCATE seat_request");

		// save passengers and requests
		for (Ride ride : RideCatalog.getInstance().getRides()) {
			// if not in the past... rides in the past cannot be modified during execution so there is no need to update in DB
			if (LocalDate.now().compareTo(ride.getDate()) <= 0) {
				for (User passenger : ride.getPassengerList()) {
					String insertStatement = String.format("INSERT IGNORE INTO seat VALUES (%s,%s)",
							ride.getRideId(), passenger.getUserId());
					statement.executeUpdate(insertStatement);
				}
				// also requests for rides in the past make no sense

				// save seat requests
				for (User requestant : ride.getRequestList()) {
					String insertStatement = String.format("INSERT INTO seat_request VALUES (%s,%s)",
							ride.getRideId(), requestant.getUserId());
					statement.executeUpdate(insertStatement);
				}
			}
		}

	}

	public void deletePassenger(int rideID, int passengerID) throws DBConnectionException, SQLException {
		DBConnectionManager.getInstance().openConnection();

		Statement stmt = DBConnectionManager.getInstance().getStatement();

		String sql = String.format("DELETE FROM seat WHERE ride = %s AND passenger = %s",
				rideID, passengerID);
		stmt.executeUpdate(sql);

		stmt.close();

		DBConnectionManager.getInstance().closeConnection();
	}

	public void addNewRide(Ride ride) {
		this.newRides.add(ride);
	}

	public void addDeletedRide(Ride ride) {
		this.deletedRides.add(ride);
	}
}
