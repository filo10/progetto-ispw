package it.uniroma2.progettoispw.briscese.ui.gui2.passenger;

import it.uniroma2.progettoispw.briscese.bean.UpgradeRequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeRequestNotFoundException;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpgradetodriverViewContorller extends MyMobileViewController implements Observer {
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
			UpgradeRequestBean bean = new UpgradeRequestBean(sessionToken.getUserId(), licensecodeTextField.getText(), datePicker.getValue().toString());

			UpgradeRequestBean receivedBean = controller.newRequest(bean);
			requestId = receivedBean.getRequestId();
			setStatusLabel(receivedBean.getStatus());
			requestButton.setDisable(true);

		} catch (UpgradeException e) {
			statusLabel.setText(e.getMessage());
		}
	}

	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	@Override
	public void update() {
		UpgradeRequestBean receivedBean = null;
		try {
			receivedBean = controller.getRequestStatus(new UpgradeRequestBean(requestId));
			setStatusLabel(receivedBean.getStatus());
		} catch (UpgradeRequestNotFoundException e) {
			statusLabel.setText("No upgrade request with requestId="+requestId);
		}
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
