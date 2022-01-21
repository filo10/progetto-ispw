package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.model.*;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;
import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.time.LocalDate;

public class UpgradeToDriverController extends Subject {


	public UpgradeToDriverController() {

	}

	public void newRequest(RequestBean rbean) {
		// create a new upgrade to driver request from a passanger
		User requestant = UserCatalog.getInstance().findUser(rbean.getUserId());
		License license = new License(rbean.getLicenseCode(), LocalDate.parse(rbean.getLicenseExpiration()));
		UpgradeRequestCatalog.getInstance().newRequest(this, requestant, license);
	}

	public void closeRequest(RequestBean rbean) {
		// "chiudi" la richiesta
		UpgradeRequest request = UpgradeRequestCatalog.getInstance().findRequest(rbean.getRequestId());
		Verifier verifier = (Verifier) UserCatalog.getInstance().findUser(rbean.getVerifierId()).getRoleInstance("verifier");
		request.close(rbean.getOutcome(), verifier);

		// se esito positivo
		if (rbean.getOutcome()) {
			try {
				// aggiungi ruolo DRIVER e la patente all'utente
				Driver newRole = new Driver(request.getLicense());
				request.getRequestant().addRole(newRole); // lancia eccezione
				request.getLicense().setOwner(newRole);
			} catch (Exception e) { // TODO gestisci CannotAddRoleException
				e.printStackTrace();
			}
		}

		// notifica l'utente dell'esito della richiesta promozione
		notifyObservers();
	}
}