package it.uniroma2.progettoispw.briscese.model;

import java.util.ArrayList;
import java.util.List;

public class UpgradeRequestCatalog {	// TODO synchronized ???
	private static UpgradeRequestCatalog instance;
	private int nextId = 1; // TODO setta il valore giusto di nextId nel costruttore...
	private List<UpgradeRequest> requests = new ArrayList<>();


	private UpgradeRequestCatalog() {}

	public static UpgradeRequestCatalog getInstance() {
		if (instance == null)
			instance = new UpgradeRequestCatalog();
		return instance;
	}

	public UpgradeRequest newRequest(User requestant, License license) {
		UpgradeRequest newRequest = new UpgradeRequest(nextId, requestant, license);
		nextId++;
		requests.add(newRequest);

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

	// TODO public UpgradeRequest findRequest(int id)
}
