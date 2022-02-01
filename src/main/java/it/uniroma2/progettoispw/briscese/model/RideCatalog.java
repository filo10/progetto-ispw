package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.exceptions.RideNotFoundException;
import it.uniroma2.progettoispw.briscese.exceptions.RoleException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RideCatalog {
	private static RideCatalog instance = null;

	private int nextId; // TODO vedi requestCatalog. setta da DB
	private List<Ride> rideList;


	private RideCatalog() {
		rideList = new ArrayList<>();
	}

	public static synchronized RideCatalog getInstance() {
		if (RideCatalog.instance == null)
			RideCatalog.instance = new RideCatalog();
		return instance;
	}

	public Ride newRide(User driver, int numberOfSeats, String startPoint, String finishPoint, LocalDate date, LocalTime time) throws RoleException {
		Ride ride = new Ride(nextId, driver, numberOfSeats, startPoint, finishPoint, date, time);
		rideList.add(ride);
		nextId++;

		return ride;
	}

	public List<Ride> getAvailableRides() {
		List<Ride> list = new ArrayList<>();
		for (Ride ride : rideList) {
			if ((ride.getDate().compareTo(LocalDate.now()) >= 0) && (ride.isAvailable()) )
				list.add(ride);
		}
		return list;
	}

	public List<Ride> getRides() {
		return rideList;
	}

	public Ride findRide(int rideId) throws RideNotFoundException {
		for (Ride ride : rideList) {
			if (ride.getRideId() == rideId)
				return ride;
		}
		throw new RideNotFoundException(rideId);
	}

}