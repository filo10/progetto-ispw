package it.uniroma2.progettoispw.briscese.ui.gui1;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.observer_gof.Observer;
import it.uniroma2.progettoispw.briscese.ui.SessionToken;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UpgradetodriverPageController implements Observer, PageController {
	private UpgradeToDriverController logicController;
	private SessionToken token;
	private int requestId;

	@FXML private TextField codeTextField;
	@FXML private TextField expirationTextField;
	@FXML private Label statusLabel;

	public void onRequestUpgradeButtonClick() {
		try {
			logicController = new UpgradeToDriverController();

			if (!checkExpirationTextFieldIsCorrect(expirationTextField.getText()))
				throw new UpgradeException("Date must be valid and in YYYY-MM-DD format.");

			RequestBean bean = new RequestBean(token.getUserId(), codeTextField.getText(), expirationTextField.getText());
			RequestBean receivedBean = logicController.newRequest(bean);
			logicController.attach(this);
			requestId = receivedBean.getRequestId();
			statusLabel.setText("Pending...");
		} catch (UpgradeException e) {
			statusLabel.setText(e.getMessage());
		}
	}

	private boolean checkExpirationTextFieldIsCorrect(String date) {
		try {
			LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	@Override
	public void update() {
		RequestBean bean = logicController.getRequestStatus(new RequestBean(requestId));
		String status = null;
		switch (bean.getStatus()) {
			case 0:
				status = "Pending...";
				break;
			case -1:
				status = "Rejected.";
				break;
			case 1:
				status = "Approved! Log out and log in again to use your Driver account";
				break;
			default:
				status = "Something went wrong, please contact Verifier.";
		}
		statusLabel.setText(status);
	}

	@Override
	public void shareSessionToken(SessionToken token) {
		this.token = token;
	}

}
