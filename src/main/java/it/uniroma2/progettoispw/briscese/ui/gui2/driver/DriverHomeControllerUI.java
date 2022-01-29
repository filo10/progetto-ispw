package it.uniroma2.progettoispw.briscese.ui.gui2.driver;

import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DriverHomeControllerUI extends MyMobileViewController {
	@FXML private Label label;

	//private ObserverBean observerBean;


	public void onNavigationButtonClick(ActionEvent event) {
		nextViewAndRememberThisScene(event);
	}

}
