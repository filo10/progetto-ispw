package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;


import java.time.LocalDate;

public class UpgradeRequest {
	private int requestId;
	private User requestant;
	private String licenseCode;
	private LocalDate licenseExpiration;
	private LocalDate requestDate;
	private Verifier verifier;
	private UpgradeRequestStatus status;

	private UpgradeToDriverController controller;


	public UpgradeRequest(UpgradeToDriverController controller, int id, User user, String licenseCode, LocalDate licenseExpiration) {
		this.controller = controller;
		requestId = id;
		requestant = user;
		this.licenseCode = licenseCode;
		this.licenseExpiration = licenseExpiration;
		requestDate = LocalDate.now();
		status = UpgradeRequestStatus.PENDING;
	}

	public void close(boolean outcome, Verifier verifier) {
		this.verifier = verifier;
		if (outcome) {
			status = UpgradeRequestStatus.APPROVED;
		}
		else
			status = UpgradeRequestStatus.REJECTED;
	}

	public UpgradeRequestStatus getStatus() {
		return status;
	}

	public int getRequestId() {
		return requestId;
	}

	public User getRequestant() {
		return requestant;
	}

	public License getLicense() {
		if (status == UpgradeRequestStatus.APPROVED)
			return new License(licenseCode, licenseExpiration);
		return null;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public LocalDate getLicenseExpiration() {
		return licenseExpiration;
	}

	public UpgradeToDriverController getController() {
		return controller;
	}
}
