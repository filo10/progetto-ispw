package it.uniroma2.progettoispw.briscese.bean;

public class SeatReplyBean {
	private int rideId;
	private int passengerId;
	private boolean reply;


	public SeatReplyBean(int rideId, int passengerId, boolean reply) {
		this.rideId = rideId;
		this.passengerId = passengerId;
		this.reply = reply;
	}

	public int getRideId() {
		return rideId;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public boolean getReply() {
		return reply;
	}
}
