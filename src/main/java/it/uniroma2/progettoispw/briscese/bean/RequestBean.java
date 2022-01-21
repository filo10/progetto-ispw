package it.uniroma2.progettoispw.briscese.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestBean {
	private int requestId;
	private int userId;
	private String licenseCode;
	private String licenseExpiration;
	private String requestDate;
	private boolean outcome;
	private int verifierId;


	public RequestBean(int requestId, int userId, String licenseCode, LocalDate licenseExpiration) {
		this.requestId = requestId;
		this.userId = userId;
		this.licenseCode = licenseCode;

		// Creating a custom formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm a");
		// converting date to string
		String expirationString = licenseExpiration.format(formatter);

		this.licenseExpiration = expirationString;
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

	public boolean getOutcome() {
		return outcome;
	}

	public int getVerifierId() {
		return verifierId;
	}

	public int getRequestId() {
		return requestId;
	}
}
