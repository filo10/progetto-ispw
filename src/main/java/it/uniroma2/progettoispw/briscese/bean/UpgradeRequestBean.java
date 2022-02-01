package it.uniroma2.progettoispw.briscese.bean;

import it.uniroma2.progettoispw.briscese.model.UpgradeRequestStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpgradeRequestBean {
	private int requestId;
	private int userId;
	private String licenseCode;
	private String licenseExpiration;
	private String requestDate;
	private int status; // 0=pending, 1=approved, -1=rejected
	private int verifierId;


	public UpgradeRequestBean(int userId, String licenseCode, String licenseExpiration) {
		this.userId = userId;
		this.licenseCode = licenseCode;
		this.licenseExpiration = licenseExpiration;
	}

	public UpgradeRequestBean(int requestId, int userId, String licenseCode, LocalDate licenseExpiration, LocalDate requestDate) {
		this.requestId = requestId;
		this.userId = userId;
		this.licenseCode = licenseCode;

		// Creating a custom formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// converting date to string
		String expirationDateString = licenseExpiration.format(formatter);
		String requestDateString = requestDate.format(formatter);

		this.licenseExpiration = expirationDateString;
		this.requestDate = requestDateString;
	}

	public UpgradeRequestBean(int requestId, UpgradeRequestStatus status) {
		this.requestId = requestId;
		switch (status) {
			case PENDING:
				this.status = 0;
				break;
			case REJECTED:
				this.status = -1;
				break;
			case APPROVED:
				this.status = 1;
				break;
		}
	}

	public UpgradeRequestBean(int requestId) {
		this.requestId = requestId;
	}

	public int getUserId() {
		return userId;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public String getLicenseExpiration() {
		return licenseExpiration;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		if (status < 0) // rejected
			this.status = -1;
		else if (status > 0) // approved
			this.status = 1;
		else
			this.status = 0; // pending
	}

	public int getVerifierId() {
		return verifierId;
	}

	public int getRequestId() {
		return requestId;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public void setVerifierId(int verifierId) {
		this.verifierId = verifierId;
	}
}
