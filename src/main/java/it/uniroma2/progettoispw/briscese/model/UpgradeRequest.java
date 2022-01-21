package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;
import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.time.LocalDate;

public class UpgradeRequest {
	private int requestId;
	private User requestant;
	private License license;
	private LocalDate requestDate;
	private Verifier verifier;
	private UpgradeRequestStatus status;

	private UpgradeToDriverController controller;


	public UpgradeRequest(UpgradeToDriverController controller, int id, User user, License license) {
		this.controller = controller;
		requestId = id;
		requestant = user;
		this.license = license;
		requestDate = LocalDate.now();
		status = UpgradeRequestStatus.PENDING;
	}

	public void close(boolean outcome, Verifier verifier) {
		this.verifier = verifier;
		if (outcome)
			status = UpgradeRequestStatus.APPROVED;
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
		return license;
	}

	public UpgradeToDriverController getController() {
		return controller;
	}
}
