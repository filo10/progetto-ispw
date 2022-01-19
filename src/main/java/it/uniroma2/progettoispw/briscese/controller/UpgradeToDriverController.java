package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.model.License;
import it.uniroma2.progettoispw.briscese.model.UpgradeRequestCatalog;
import it.uniroma2.progettoispw.briscese.model.User;

public class UpgradeToDriverController {

	private RequestBean bean;
	private License license;


	public void newRequest(RequestBean rb) {
		// crea una nuova richiesta
		User requestant;
		license = new License(rb.getLicenseCode(), rb.getLicenseExpiration());
		UpgradeRequestCatalog.getInstance().newRequest(requestant, license);

		// TODO mi devo ricordare del bean come attributo qui?
		// salvare riferimenti delle view nel bean
		// come raggiungere questa istanza di controller da view del verifier?

		// manda una notifica a VERIFIER

	}

	// TODO get pending requests

	public void closeRequest(RequestBean rb) {
		// "chiudi" la richiesta

		// se esito positivo
			// crea patente
			// aggiungi ruolo DRIVER e la patente all'utente

		// notifica l'utente
	}
}
