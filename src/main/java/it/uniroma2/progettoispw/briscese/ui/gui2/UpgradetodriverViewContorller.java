package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpgradetodriverViewContorller extends MyViewController implements Observer {
	@FXML private TextField licensecodeTextField;
	@FXML private DatePicker datePicker;
	@FXML private Label statusLabel;
	@FXML private Button requestButton;
	private int requestId;
	private UpgradeToDriverController controller = new UpgradeToDriverController();

	@FXML
	public void initialize() {
		controller.attach(this);
	}

	public void onRequestButtonClick() {
		try {
			RequestBean bean = new RequestBean(sessionToken.getUserId(), licensecodeTextField.getText(), datePicker.getValue().toString());

			RequestBean receivedBean = controller.newRequest(bean);
			requestId = receivedBean.getRequestId();
			setStatusLabel(receivedBean.getStatus());
			requestButton.setDisable(true);

		} catch (UpgradeException e) {
			statusLabel.setText(e.getMessage());
		}
	}

	public void onBackButtonClick() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/it/uniroma2/progettoispw/gui2/psngr-home.fxml"));
			Stage window = (Stage) statusLabel.getScene().getWindow();
			Scene newScene = new Scene(loader.load());
			window.setScene(newScene);

			MyViewController controller = loader.getController();
			controller.setSessionToken(sessionToken);

			window.show();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
	}

	@Override
	public void update() {
		RequestBean receivedBean = controller.getRequestStatus(new RequestBean(requestId));
		setStatusLabel(receivedBean.getStatus());
	}

	private void setStatusLabel(int requestStatus) {
		switch (requestStatus) {
			case 1:
				statusLabel.setText("Request accepted!\nLog out and log in to use your Driver profile");
				break;
			case -1:
				statusLabel.setText("Request rejected.");
				break;
			case 0:
				statusLabel.setText("Pending...");
				break;
			default:
				statusLabel.setText("Something went wrong...\nPlease contact Verifier");
		}
	}
}
