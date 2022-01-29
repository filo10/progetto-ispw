package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;

import java.time.LocalDate;

public class UpgradeRequest {
	private int requestId;
	private User requestant;
	private String licenseCode;
	private LocalDate licenseExpiration;
	private LocalDate requestDate;
	private User verifier;
	private UpgradeRequestStatus status;
	private LocalDate verificationDate;

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

	public void close(boolean outcome, User verifier) {
		if (!verifier.hasRole("verifier"))
			return;

		this.verifier = verifier;
		this.verificationDate = LocalDate.now();

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

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public void setVerifier(User verifier) {
		this.verifier = verifier;
	}

	public void setStatus(UpgradeRequestStatus status) {
		this.status = status;
	}

	public void setVerificationDate(LocalDate verificationDate) {
		this.verificationDate = verificationDate;
	}

	public LocalDate getVerificationDate() {
		return verificationDate;
	}

	public User getVerifier() {
		return verifier;
	}
}
