package it.uniroma2.progettoispw.briscese.ui.gui2.verifier;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeRequestNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VerifyrequestViewController extends MyMobileViewController {
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
			if ((requestController = controller.getRequestController(requestId)) == null) {
				infoLabel.setText("No request with ID=" + requestId);
				setDisableDecisionButtons(true);
				return;
			}
			fillInfoLabel(requestController.getRequestInfo(new RequestBean(requestId)));
			setDisableDecisionButtons(false);
		} catch (NumberFormatException e) {
			infoLabel.setText("RequestID must be a number");
			setDisableDecisionButtons(true);
		} catch (UpgradeRequestNotFoundException e) {
			infoLabel.setText("No Upgrade Request with this ID");
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

		int status = 0;
		if (pressedButton.equals(approveButton)) {
			status = 1;
		} else if (pressedButton.equals(rejectButton)) {
			status = -1;
		}
		bean.setStatus(status);

		try {
			requestController.closeRequest(bean);
			setDisableDecisionButtons(true);
			if (status > 0) {
				errorLabel.setText("DONE. Request accepted");
			}
			else {
				errorLabel.setText("DONE. Request rejected");
			}
		} catch (UpgradeException e) {
			errorLabel.setText(e.getMessage());
		}
	}

	public void onBackButtonClick(ActionEvent event) {
		nextView(event);
	}

	private void setDisableDecisionButtons(boolean disable) {
		approveButton.setDisable(disable);
		rejectButton.setDisable(disable);
	}

}
