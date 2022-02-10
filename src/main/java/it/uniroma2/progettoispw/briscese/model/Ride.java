package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.dao.RideDAO;
import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Ride {
	private int rideId;
	private User driver;

	private int numberOfSeats;
	private List<User> passengerList;
	private List<User> requestList;

	private String startPoint;
	private String finishPoint;
	private LocalDate date;
	private LocalTime time;


	public Ride(int nextId, User driver, int numberOfSeats, String startPoint, String finishPoint, LocalDate date, LocalTime time) throws RoleException {
		if (!driver.hasRole("driver"))
			throw new RoleException("The user is not a Driver (userID=" + driver.getUserId() + ")");

		this.rideId = nextId;
		this.driver = driver;
		this.numberOfSeats = numberOfSeats;
		this.startPoint = startPoint;
		this.finishPoint = finishPoint;
		this.date = date;
		this.time = time;

		this.passengerList = new ArrayList<>();
		this.requestList = new ArrayList<>();
	}

	public void requestSeat(User passenger) throws SeatRequestException {
		if (!passenger.hasRole("passenger"))
			throw new SeatRequestException("You must be a passenger to request a seat.");
		if (date.isBefore(LocalDate.now()))
			throw new SeatRequestException("You can't send a request for a ride in the past.");
		if (passenger.getUserId() == driver.getUserId())
			throw new SeatRequestException("You can't send a request for your rides!");
		if (passengerList.size() < numberOfSeats) {
			if (requestList.contains(passenger))
				throw new SeatRequestException("You already send a request for this ride.");
			if (passengerList.contains(passenger))
				throw new SeatRequestException("You are already a passenger for this ride.");
			requestList.add(passenger);
			driver.publishNotification("New seat request for " + this);
		} else {
			throw new SeatRequestException("Cannot send request: the ride is full.");
		}
	}

	public void cancelSeatRequest(User passenger) throws SeatRequestException {
		if (!requestList.remove(passenger)) {
			throw new SeatRequestException("You didn't request a seat for this ride.");
		}
	}

	public void replyRequest(User passenger, boolean answer) throws SeatRequestException {
		if (date.isBefore(LocalDate.now()))
			throw new SeatRequestException("You can't reply a request for a ride in the past.");
		if (passengerList.size() >= numberOfSeats)
			throw new SeatRequestException("Can't add another passenger. The ride is full.");
		if (!requestList.contains(passenger))
			throw new SeatRequestException("This user has not requested a seat for this ride.");
		if (answer) {
			requestList.remove(passenger);
			passengerList.add(passenger);
			passenger.publishNotification("You've been accepted for " + this);
		} else {
			requestList.remove(passenger);
			passenger.publishNotification("Your request has been rejected " + this);
		}
	}

	public void removePassenger(User passenger) throws SeatRequestException, SQLException, DBConnectionException {
		if (date.isBefore(LocalDate.now()))
			throw new SeatRequestException("You can't remove passengers from a ride in the past.");
		if (!passengerList.contains(passenger))
			throw new SeatRequestException("The user you want to remove is not a passenger of this ride.");
		if (passengerList.remove(passenger)) {
			RideDAO.getInstance().deletePassenger(this.rideId, passenger.getUserId());
			passenger.publishNotification("You've been removed from " + this);
		}
	}

	public void leaveRide(User passenger) throws SeatRequestException, SQLException, DBConnectionException {
		if (date.isBefore(LocalDate.now()))
			throw new SeatRequestException("You can't leave a ride in the past.");
		if (passengerList.remove(passenger)) {
			RideDAO.getInstance().deletePassenger(this.rideId, passenger.getUserId());
			driver.publishNotification("The passenger " + passenger.getUserId() + " left: " + this);
		}
		else
			throw new SeatRequestException("You are not a passenger for this ride.");
	}

	public void deleteRide() {
		for (User passenger : passengerList)
			passenger.publishNotification(this + " is CANCELLED");
		for (User requestingUser : requestList)
			requestingUser.publishNotification(this + " is CANCELLED");
		RideCatalog.getInstance().deleteRide(this);
	}

	@Override
	public String toString() {
		return String.format("Ride #%d [on %s, %s]%nto: %s, from: %s",
				rideId, date.toString(), time.toString(), finishPoint, startPoint);
	}

	public LocalDate getDate() {
		return date;
	}

	public boolean isAvailable() {
		return passengerList.size() < numberOfSeats;
	}

	public int getRideId() {
		return rideId;
	}

	public User getDriver() {
		return driver;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public String getFinishPoint() {
		return finishPoint;
	}

	public LocalTime getTime() {
		return time;
	}

	public int seatsAvailable() {
		return numberOfSeats - passengerList.size();
	}

	public List<User> getRequestList() {
		return requestList;
	}

	public List<User> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<User> passengerList) {
		this.passengerList = passengerList;
	}

	public void setRequestList(List<User> requestList) {
		this.requestList = requestList;
	}
}
