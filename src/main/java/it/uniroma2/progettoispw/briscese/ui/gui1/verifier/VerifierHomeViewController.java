package it.uniroma2.progettoispw.briscese.ui.gui1.verifier;

import it.uniroma2.progettoispw.briscese.bean.UpgradeRequestBean;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.gui1.HomeViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;


public class VerifierHomeViewController extends HomeViewController implements Observer {
	private VerifyRequestsController logicController = new VerifyRequestsController();
	@FXML private VBox vbox;
	@FXML private Label notificationLabel;

	@FXML
	public void initialize() {
		logicController.attach(this);
	}

	public void onRefreshButtonClick() {
		try {
			vbox.getChildren().clear();
			List<UpgradeRequestBean> requests = logicController.getPendingRequests();

			for (UpgradeRequestBean bean : requests) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/it/uniroma2/progettoispw/gui1/items/requestslist-item.fxml"));
				Node item = loader.load();

				// prendi il controller per init l'item
				RequestslistItemController itemViewController = loader.getController();
				itemViewController.setCallerAndSessionToken(this, sessionToken);
				itemViewController.setUserModel(bean);
				itemViewController.inject();

				vbox.getChildren().add(item);
			}
			notificationLabel.setVisible(false);
		} catch (IOException e) {
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
		}
	}

	@Override
	public void update() {
		notificationLabel.setVisible(true);
	}

	@Override
	public void logout(ActionEvent event) {
		super.logout(event);
		logicController.detach(this);
	}



	// TODO uniformare view?
	// TODO uniformare notifiche?
}
