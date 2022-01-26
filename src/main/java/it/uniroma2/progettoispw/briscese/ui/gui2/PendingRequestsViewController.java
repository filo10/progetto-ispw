package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class PendingRequestsViewController extends MyViewController implements Observer {
	@FXML private Label notificationLabel;
	@FXML private ListView<String> listView;
	private VerifyRequestsController controller = new VerifyRequestsController();

	@FXML
	public void initialize() {
		controller.attach(this);
	}

	public void onRefreshButtonClick() {
		listView.getItems().clear();

		for (RequestBean b : controller.getPendingRequests()) {
			String item = "ReqID: " + b.getRequestId() + " (" + b.getRequestDate() + ")" +
					"\nUser: " + b.getUserId() +
					"\nLic.Code: " + b.getLicenseCode() +
					"\nLic.Exp: " + b.getLicenseExpiration();
			listView.getItems().add(item);
		}
		notificationLabel.setVisible(false);
	}

	public void onBackButtonClick() {
		try {
			FXMLLoader loader = new FXMLLoader();
			Stage window = (Stage) notificationLabel.getScene().getWindow();
			loader.setLocation(getClass().getResource("/it/uniroma2/progettoispw/gui2/verifier-home.fxml"));
			Scene newScene = new Scene(loader.load());
			MyViewController nextViewController = loader.getController();
			nextViewController.setSessionToken(sessionToken);
			window.setScene(newScene);
			window.show();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
	}

	@Override
	public void update() {
		notificationLabel.setVisible(true);
	}
}
