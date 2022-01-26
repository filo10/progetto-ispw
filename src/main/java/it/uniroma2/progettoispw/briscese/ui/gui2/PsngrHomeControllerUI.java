package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PsngrHomeControllerUI extends MyViewController {
	@FXML private Label label;

	// TODO
	public void onReserveButtonClick() {
		nextView("/it/uniroma2/progettoispw/gui2/reserveride-view.fxml");
	}

	// TODO
	public void onMyReservationsButtonClick() {
		nextView("/it/uniroma2/progettoispw/gui2/myreservations-view.fxml");
	}

	public void onUpgradeButtonClick() {
		nextView("/it/uniroma2/progettoispw/gui2/upgradetodriver-view.fxml");
	}

	public void onLogoutButtonClick() {
		Stage thisWindow = (Stage) label.getScene().getWindow();
		thisWindow.close();
	}

	private void nextView(String resName){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(resName));
			Stage window = (Stage) label.getScene().getWindow();
			Scene newScene = new Scene(loader.load());
			window.setScene(newScene);

			MyViewController controller = loader.getController();
			controller.setSessionToken(sessionToken);

			window.show();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
	}
}
