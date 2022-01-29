package it.uniroma2.progettoispw.briscese.ui.gui2.passenger;

import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PsngrHomeControllerUI extends MyMobileViewController {
	@FXML private Label label;

	public void onNavigationButtonClick(ActionEvent event) {
		nextViewAndRememberThisScene(event);
	}

}
