package it.uniroma2.progettoispw.briscese.bean;

import it.uniroma2.progettoispw.briscese.observer_gof.Observer;

public class RequestBean implements Observer {
	private int requestId;
	private int userId;
	private String licenseCode;
	private String licenseExpiration;
	private String requestDate;
	private boolean outcome;


	public void notifyViews() {

	}

	@Override
	public void update() {

	}
}
