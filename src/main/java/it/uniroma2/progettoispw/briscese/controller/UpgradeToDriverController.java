package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.dao.UserDAO;
import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeRequestNotFoundException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.model.*;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;
import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.time.LocalDate;

public class UpgradeToDriverController extends Subject {

	public RequestBean newRequest(RequestBean rbean) throws UpgradeException {
		// create a new upgrade to driver request from a passanger
		try {
			User requestant = UserCatalog.getInstance().findUser(rbean.getUserId());

			if (!(requestant.hasRole("passenger") && !requestant.hasRole("driver")))
				throw new UpgradeException("Cannot upgrade to Driver this user");

			LocalDate licenseExpiration = LocalDate.parse(rbean.getLicenseExpiration());
			UpgradeRequest request = UpgradeRequestCatalog.getInstance().newRequest(this, requestant, rbean.getLicenseCode(), licenseExpiration);
			rbean.setStatus(0);
			rbean.setRequestId(request.getRequestId());

			return rbean;
		} catch (UserNotFoundException e) {
			// exception conversion
			throw new UpgradeException("No User was found with the ID you used.");
		}
	}

	public void closeRequest(RequestBean rbean) throws UpgradeException {
		try {
			// "chiudi" la richiesta
			UpgradeRequest request = UpgradeRequestCatalog.getInstance().findRequest(rbean.getRequestId());

			User userVerifier = UserCatalog.getInstance().findUser(rbean.getVerifierId());
			if (!userVerifier.hasRole("verifier")) {
				throw new UpgradeException("User with ID=" + rbean.getVerifierId() + " is not a verifier");
			}

			if (rbean.getStatus() == 0)
				throw new UpgradeException("You are trying to close a request assigning it PENDING status...");
			boolean outcome = rbean.getStatus() == 1;
			request.close(outcome, userVerifier);

			// se esito positivo
			if (outcome) {
				// aggiungi ruolo DRIVER e la patente all'utente
				License license = request.getLicense();
				Driver newRole = new Driver(license);
				User nowDriverUser = request.getRequestant();
				nowDriverUser.addRole(newRole);
				UserDAO.getInstance().newDriver(nowDriverUser);
			}

			notifyObservers();

		} catch (UserNotFoundException e1) {
			throw new UpgradeException("No User was found with this ID: " + rbean.getUserId());
		} catch (CannotAddRoleException e2) {
			throw new UpgradeException("The User whit ID=" + rbean.getRequestId() + " cannot be a Driver.");
		} catch (UpgradeRequestNotFoundException e) {
			throw new UpgradeException("No Upgrade request was found with this ID: " + rbean.getRequestId());
		}
	}

	public RequestBean getRequestStatus(RequestBean bean) throws UpgradeRequestNotFoundException {
		UpgradeRequest request = UpgradeRequestCatalog.getInstance().findRequest(bean.getRequestId());
		return new RequestBean(request.getRequestId(), request.getStatus());
	}

	public RequestBean getRequestInfo(RequestBean bean) throws UpgradeRequestNotFoundException {
		UpgradeRequest request = UpgradeRequestCatalog.getInstance().findRequest(bean.getRequestId());
		return new RequestBean(request.getRequestId(), request.getRequestant().getUserId(), request.getLicenseCode(), request.getLicenseExpiration(), request.getRequestDate());
	}
}