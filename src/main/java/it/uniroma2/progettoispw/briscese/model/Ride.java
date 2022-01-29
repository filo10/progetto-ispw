package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.exceptions.RoleException;

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

	public void requestSeat(User passenger) {
		if (!passenger.hasRole("passenger"))
			return;
		if (passengerList.size() < numberOfSeats) {
			requestList.add(passenger);
			driver.publishNotification("New seat request for " + this);
		}
	}

	public void replyRequest(User passenger, boolean answer) {
		if (!requestList.contains(passenger))
			return;

		if (answer) {
			requestList.remove(passenger);
			passengerList.add(passenger);
			passenger.publishNotification("You've been accepted for" + this);
		} else {
			requestList.remove(passenger);
			passenger.publishNotification("Your request has been rejected " + this);
		}
	}

	public void removePassenger(User passenger) {
		if (passengerList.remove(passenger))
			passenger.publishNotification("You've been removed from " + this);
	}

	public void leaveRide(User passenger) {
		if (passengerList.remove(passenger))
			driver.publishNotification("A passenger left this ride:%n" + this);
	}

	public void deleteRide() {
		for (User passenger : passengerList)
			passenger.publishNotification(this + "%nis CANCELLED");
		for (User requestingUser : requestList)
			requestingUser.publishNotification(this + "%nis CANCELLED");
	}

	@Override
	public String toString() {
		return String.format("Ride #%d [on %s %s]%nto: %s, from: %s",
				rideId, date.toString(), time.toString(), finishPoint, startPoint);
	}

}
