package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VerifierHomeControllerUI extends MyViewController {
	@FXML private Label verifierLabel;


	public void onPendingButtonClick() {
		nextView("/it/uniroma2/progettoispw/gui2/pendingrequests-view.fxml");
	}

	public void onVerifyButtonClick() {
		nextView("/it/uniroma2/progettoispw/gui2/verifyrequest-view.fxml");
	}

	public void onLogoutButtonClick() {
		Stage window = (Stage) verifierLabel.getScene().getWindow();
		window.close();
	}

	private void nextView(String resourcePath){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(resourcePath));
			Stage thisWindow = (Stage) verifierLabel.getScene().getWindow();
			Scene newScene = new Scene(fxmlLoader.load());
			MyViewController nextViewController = fxmlLoader.getController();
			nextViewController.setSessionToken(sessionToken);
			thisWindow.setScene(newScene);
			thisWindow.show();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
	}
}
