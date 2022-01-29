package it.uniroma2.progettoispw.briscese.model;

import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.dao.UpgradeRequestDAO;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeRequestNotFoundException;
import it.uniroma2.progettoispw.briscese.observer_gof.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpgradeRequestCatalog extends Subject {
	private static UpgradeRequestCatalog instance = null;

	private int nextId;
	private List<UpgradeRequest> requests;


	protected UpgradeRequestCatalog() {
		this.requests = new ArrayList<>();
	}

	public static synchronized UpgradeRequestCatalog getInstance() {
		if (UpgradeRequestCatalog.instance == null)
			UpgradeRequestCatalog.instance = new UpgradeRequestCatalog();
		return instance;
	}

	public UpgradeRequest newRequest(UpgradeToDriverController controller, User requestant, String licenseCode, LocalDate licenseExpiration) {
		UpgradeRequest newRequest = new UpgradeRequest(controller, nextId, requestant, licenseCode, licenseExpiration);
		nextId++;
		requests.add(newRequest);
		UpgradeRequestDAO.getInstance().newRequest(newRequest);

		notifyObservers();

		return newRequest;
	}

	public List<UpgradeRequest> getPendingRequests() {
		List<UpgradeRequest> returnList = new ArrayList<>();

		for (UpgradeRequest req : requests) {
			if (req.getStatus() == UpgradeRequestStatus.PENDING)
				returnList.add(req);
		}

		return returnList;
	}

	public UpgradeRequest findRequest(int id) throws UpgradeRequestNotFoundException {
		for (UpgradeRequest req : requests) {
			if (req.getRequestId() == id)
				return req;
		}
		throw new UpgradeRequestNotFoundException(id);
	}

	public void setRequests(List<UpgradeRequest> requests) {
		this.requests = requests;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
}
