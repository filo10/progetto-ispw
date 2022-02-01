package it.uniroma2.progettoispw.briscese.bean;

public class SeatRequestBean {
	private int rideId;
	private int passengerId;
	private String passengerName;

	public SeatRequestBean(int rideId, int passengerId) {
		this.rideId = rideId;
		this.passengerId = passengerId;
	}

	public SeatRequestBean(int rideId, int passengerId, String passengerName) {
		this.rideId = rideId;
		this.passengerId = passengerId;
		this.passengerName = passengerName;
	}

	public int getRideId() {
		return rideId;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public String getPassengerName() {
		return passengerName;
	}
}
