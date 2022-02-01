package it.uniroma2.progettoispw.briscese.exceptions;

public class RideNotFoundException extends Exception {
	public RideNotFoundException(int rideId) {
		super("A Ride with rideID=" + rideId + " does not exist.");
	}
}
