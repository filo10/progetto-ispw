package it.uniroma2.progettoispw.briscese.ui.gui1.verifier;

import it.uniroma2.progettoispw.briscese.bean.RequestBean;
import it.uniroma2.progettoispw.briscese.controller.UpgradeToDriverController;
import it.uniroma2.progettoispw.briscese.controller.VerifyRequestsController;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeException;
import it.uniroma2.progettoispw.briscese.exceptions.UpgradeRequestNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.SessionToken;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class RequestslistItemController {
	private RequestBean bean;
	private SessionToken token;
	private VerifierHomeViewController caller;

	@FXML private Label requestantLabel;
	@FXML private Label licensecodeLabel;
	@FXML private Label licenseexpirationLabel;
	@FXML private Label requestidLabel;

	public void setUserModel(RequestBean bean) {
		this.bean = bean;
	}

	public void setCallerAndSessionToken(VerifierHomeViewController caller, SessionToken token) {
		this.caller = caller;
		this.token = token;
	}

	public void inject() {
		requestantLabel.setText("" + bean.getRequestId());
		licensecodeLabel.setText(bean.getLicenseCode());
		licenseexpirationLabel.setText(bean.getLicenseExpiration());
		requestidLabel.setText(bean.getRequestId() + " (" + bean.getRequestDate() + ")");
	}

	public void onVerifyButtonClick() {
		String approve = "Approve";
		String rejet = "Reject";

		//Creating a choice box
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(approve);
		//Retrieving the observable list
		ObservableList<String> list = choiceDialog.getItems();
		//Adding items to the list
		list.add(approve);
		list.add(rejet);
		// show dialog
		Optional<String> result = choiceDialog.showAndWait();
		String selected = rejet;
		if (result.isPresent()) {
			selected = result.get();
		}

		//prendi il controller della richiesta e "chiudila"
		VerifyRequestsController c = new VerifyRequestsController();

		try {
			UpgradeToDriverController sharedWithPassengerController= c.getRequestController(bean.getRequestId());

			switch (selected) {
				case "Approve":
					bean.setStatus(1);
					break;
				case "Reject":
					bean.setStatus(-1);
					break;
				default:
					throw new UpgradeException("Please select APPROVE or REJECT");
			}

			bean.setVerifierId(token.getUserId());
			sharedWithPassengerController.closeRequest(bean);
			caller.onRefreshButtonClick();

		} catch (UpgradeException | UpgradeRequestNotFoundException e) {
			//Creating a dialog
			Dialog<String> dialog = new Dialog<>();
			//Adding buttons to the dialog pane
			ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(type);

			dialog.setTitle("Verification failed");
			dialog.setContentText(e.getMessage());
			dialog.showAndWait();
		}
	}
}
