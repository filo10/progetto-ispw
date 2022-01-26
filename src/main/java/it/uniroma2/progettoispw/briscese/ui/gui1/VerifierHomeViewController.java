package it.uniroma2.progettoispw.briscese.ui.gui1;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class VerifierHomeViewController extends HomeViewController implements Observer {
	private VerifyRequestsController logicController = new VerifyRequestsController();
	@FXML private VBox vbox;
	@FXML private Label nameLabel;
	@FXML private Label roleLabel;
	@FXML private Label notificationLabel;

	@FXML
	public void initialize() {
		logicController.attach(this);
	}

	public void onRefreshButtonClick() {
		try {
			vbox.getChildren().clear();
			List<RequestBean> requests = logicController.getPendingRequests();

			for (RequestBean bean : requests) {
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
			alertDialogMissingFXML();
		}
	}

	@Override
	public void update() {
		notificationLabel.setVisible(true);
	}

	public void onLogoutButtonClick() {
		logout();
	}

	@Override
	public void injectSessionToken() {
		nameLabel.setText(sessionToken.getName() + "\n(" + sessionToken.getUserId() + ")");
		roleLabel.setText(sessionToken.getRole());
	}

	@Override
	public void logout() {
		Stage thisWindow = (Stage) nameLabel.getScene().getWindow();
		thisWindow.close();
	}
}
