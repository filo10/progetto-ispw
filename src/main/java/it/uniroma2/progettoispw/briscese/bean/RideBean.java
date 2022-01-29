package it.uniroma2.progettoispw.briscese.bean;

public class RideBean {
	private int rideId;
	private int driverId;
	private int numberOfSeats;
	private String startPoint;
	private String finishPoint;
	private String date;
	private String time;


	public int getRideId() {
		return rideId;
	}

	public int getDriverId() {
		return driverId;
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

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public void setFinishPoint(String finishPoint) {
		this.finishPoint = finishPoint;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
