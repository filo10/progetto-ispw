package it.uniroma2.progettoispw.briscese.dao;

import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.model.*;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpgradeRequestDAO {
	private static UpgradeRequestDAO instance = null;
	private List<UpgradeRequest> newRequests;


	private UpgradeRequestDAO() {
		newRequests = new ArrayList<>();
	}

	public static synchronized UpgradeRequestDAO getInstance() {
		if (UpgradeRequestDAO.instance == null)
			UpgradeRequestDAO.instance = new UpgradeRequestDAO();
		return instance;
	}

	public void retrieveUpgradeRequests() throws DBConnectionException, SQLException, UserNotFoundException {
		List<UpgradeRequest> list = new ArrayList<>();

		Statement stmt = DBConnectionManager.getInstance().getStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM license_request");

		while (rs.next()) {
			int requestId = rs.getInt("id");
			User requestant = UserCatalog.getInstance().findUser(rs.getInt("user"));
			String licenseCode = rs.getString("license_code");
			String licenseExpirationStr = rs.getString("license_expiration");
			LocalDate licenseExpiration;
			licenseExpiration = LocalDate.parse(licenseExpirationStr);

			UpgradeRequest request = new UpgradeRequest(null, requestId, requestant, licenseCode, licenseExpiration);

			String requestDateStr = rs.getString("request_date");
			LocalDate requestDate;
			requestDate = LocalDate.parse(requestDateStr);
			request.setRequestDate(requestDate);

			String verificationDateStr = rs.getString("verification_date");
			LocalDate verificationDate;
			verificationDate = LocalDate.parse(verificationDateStr);
			request.setVerificationDate(verificationDate);

			User verifier = UserCatalog.getInstance().findUser(rs.getInt("verifier"));
			request.setVerifier(verifier);

			String statusStr = rs.getString("status");
			UpgradeRequestStatus status;
			if (statusStr.equals("success"))
				status = UpgradeRequestStatus.APPROVED;
			else // REJECTED or PENDING (shouldn't exist pending requests...)
				status = UpgradeRequestStatus.REJECTED;
			request.setStatus(status);

			list.add(request);
		}

		rs = stmt.executeQuery("SELECT max(id) AS maxId FROM license_request");
		rs.next();
		int nextId = rs.getInt("maxId") + 1;

		stmt.close();

		UpgradeRequestCatalog.getInstance().setNextId(nextId);
		UpgradeRequestCatalog.getInstance().setRequests(list);
	}

	public void saveUpgradeRequests() throws DBConnectionException, SQLException {
		Statement stmt = DBConnectionManager.getInstance().getStatement();

		// get upgrade requested in this execution of the app
		for (UpgradeRequest request : newRequests) {
			// rifiuta se ce ne sono ancora aperte.
			String status;
			int verifierId;
			switch (request.getStatus()) {
				case PENDING:
					request.setVerificationDate(LocalDate.now());
					verifierId = 11; // default used in case request not verified during execution
					status = "rejected";
					break;
				case REJECTED:
					status = "rejected";
					verifierId = request.getVerifier().getUserId();
					break;
				case APPROVED:
					status = "success";
					verifierId = request.getVerifier().getUserId();
					break;
				default:
					throw new IllegalArgumentException();
			}


			//SQL insert
			String insertStatement =
					String.format("INSERT INTO license_request (id, user, license_code, license_expiration, request_date, status, verifier, verification_date)" +
									"VALUES (%s,%s,'%s','%s','%s','%s',%s,'%s')",
					request.getRequestId(), request.getRequestant().getUserId(), request.getLicenseCode(), request.getLicenseExpiration().toString(),
							request.getRequestDate().toString(), status, verifierId, request.getVerificationDate().toString());
			stmt.executeUpdate(insertStatement);
		}

		stmt.close();
	}

	public void newRequest(UpgradeRequest request) {
		newRequests.add(request);
	}
}
