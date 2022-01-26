package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VerifyrequestViewController extends MyViewController {
	@FXML private Label infoLabel;
	@FXML private Label errorLabel;
	@FXML private Button approveButton;
	@FXML private Button rejectButton;
	@FXML private TextField requestidTextField;
	private int requestId;
	private UpgradeToDriverController requestController;


	public void onSearchButtonClick() {
		try {
			VerifyRequestsController controller = new VerifyRequestsController();
			requestId = Integer.parseInt(requestidTextField.getText());
			requestController = controller.getRequestController(requestId);
			fillInfoLabel(requestController.getRequestInfo(new RequestBean(requestId)));
		} catch (NumberFormatException e) {
			infoLabel.setText("RequestID must be a number");
		}
	}

	private void fillInfoLabel(RequestBean bean) {
		String string = "Request ID: " + bean.getRequestId() +
				"\nRequest date: " + bean.getRequestDate() +
				"\nUser ID: " + bean.getUserId() +
				"\nLicense Code: " + bean.getLicenseCode() +
				"\nLicense Expiration Date: " + bean.getLicenseExpiration();
		infoLabel.setText(string);
	}

	public void onDecisionButtonClick(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();

		RequestBean bean = new RequestBean(requestId);
		bean.setVerifierId(sessionToken.getUserId());
		if (pressedButton.equals(approveButton)) {
			bean.setStatus(1);
		} else if (pressedButton.equals(rejectButton)) {
			bean.setStatus(-1);
		}

		try {
			requestController.closeRequest(bean);
		} catch (UpgradeException e) {
			errorLabel.setText(e.getMessage());
		}
	}

	public void onBackButtonClick() {
		try {
			Stage window = (Stage) infoLabel.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
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

}
