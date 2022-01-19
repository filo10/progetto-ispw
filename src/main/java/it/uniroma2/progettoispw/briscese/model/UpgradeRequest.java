package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.time.LocalDate;

public class UpgradeRequest extends Subject {
	private int requestId;
	private User requestant;
	private License license;
	private LocalDate requestDate;
	private User verifier;
	private UpgradeRequestStatus status;


	public UpgradeRequest(int id, User user, License license) {
		requestId = id;
		requestant = user;
		this.license = license;
		requestDate = LocalDate.now();
		status = UpgradeRequestStatus.PENDING;
	}

	public void close(boolean outcome) {
		if (outcome)
			status = UpgradeRequestStatus.APPROVED;
		else
			status = UpgradeRequestStatus.REJECTED;
	}

	public UpgradeRequestStatus getStatus() {
		return status;
	}
}
