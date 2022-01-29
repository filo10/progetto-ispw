package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RideCatalog {
	private static RideCatalog instance = null;

	private int nextId; // TODO vedi requestCatalog
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
}
