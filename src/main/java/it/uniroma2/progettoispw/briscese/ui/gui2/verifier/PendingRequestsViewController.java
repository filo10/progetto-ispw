package it.uniroma2.progettoispw.briscese.ui.gui2.verifier;

import it.uniroma2.progettoispw.briscese.bean.UpgradeRequestBean;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PendingRequestsViewController extends MyMobileViewController implements Observer {
	@FXML private Label notificationLabel;
	@FXML private ListView<String> listView;
	private VerifyRequestsController controller = new VerifyRequestsController();

	@FXML
	public void initialize() {
		controller.attach(this);
	}

	public void onRefreshButtonClick() {
		listView.getItems().clear();

		for (UpgradeRequestBean b : controller.getPendingRequests()) {
			String item = "ReqID: " + b.getRequestId() + " (" + b.getRequestDate() + ")" +
					"\nUser: " + b.getUserId() +
					"\nLic.Code: " + b.getLicenseCode() +
					"\nLic.Exp: " + b.getLicenseExpiration();
			listView.getItems().add(item);
		}
		notificationLabel.setVisible(false);
	}

	public void onBackButtonClick(ActionEvent event) {
		nextView(event);
		controller.detach(this);
	}

	@Override
	public void update() {
		notificationLabel.setVisible(true);
	}
}
