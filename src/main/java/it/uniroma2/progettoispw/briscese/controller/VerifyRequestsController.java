package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.model.UpgradeRequest;
import it.uniroma2.progettoispw.briscese.model.UpgradeRequestCatalog;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.util.ArrayList;
import java.util.List;

public class VerifyRequestsController extends Subject implements Observer {
	// observs UpgradeRequestCatalog for new requests,
	// sends notification to view layer classes subscribed to one instance of this class

	public VerifyRequestsController() {
		UpgradeRequestCatalog.getInstance().attach(this);
	}

	@Override
	public void update() {
		notifyObservers(); // ho ricevuto una notifica da quello che osservavo, notifico chi mi osserva. questo dovrà richiamare getpending
	}

	public List<RequestBean> getPendingRequests() {
		List<RequestBean> list = new ArrayList<>();

		for (UpgradeRequest ur : UpgradeRequestCatalog.getInstance().getPendingRequests()) {
			RequestBean newBean = new RequestBean(ur.getRequestId(), ur.getRequestant().getUserId(), ur.getLicenseCode(), ur.getLicenseExpiration(), ur.getRequestDate());
			list.add(newBean);
		}

		return list;
	}

	public UpgradeToDriverController getRequestController(int requestId) {
		UpgradeRequest req = UpgradeRequestCatalog.getInstance().findRequest(requestId);
		// TODO
		/*
		if (req == null)
			throw new Exception();
		 */
		return req.getController();
	}


	// TODO staccati (quando il verifier fa log out)
	public void onLogOut() {
		UpgradeRequestCatalog.getInstance().detach(this);
	}

}
