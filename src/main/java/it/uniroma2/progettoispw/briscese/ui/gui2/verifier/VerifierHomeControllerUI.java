package it.uniroma2.progettoispw.briscese.ui.gui2.verifier;

import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VerifierHomeControllerUI extends MyMobileViewController {
	@FXML private Label verifierLabel;

	public void onButtonClick(ActionEvent event) {
		nextView(event);
	}

}
