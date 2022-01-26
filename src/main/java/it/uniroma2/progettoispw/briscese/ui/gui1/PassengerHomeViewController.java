package it.uniroma2.progettoispw.briscese.ui.gui1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PassengerHomeViewController extends HomeViewController {
	@FXML private BorderPane borderPane;
	@FXML private VBox sidebarButtons;
	@FXML private Label nameLabel;
	@FXML private Label roleLabel;


	public void onLogoutButtonClick() {
		logout();
	}

	@Override
	public void injectSessionToken() {
		nameLabel.setText(sessionToken.getName() + "\n(" + sessionToken.getUserId() + ")");
		roleLabel.setText(sessionToken.getRole());
	}

	@Override
	public void logout() {
		Stage thisWindow = (Stage) nameLabel.getScene().getWindow();
		thisWindow.close();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public void onAPageButtonClick(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();
		highlightPressedButton(pressedButton);
		PageController pageController = setContentArea(pressedButton.getUserData().toString()); // the name of the fxml file to open is inside button's user data attribute
		pageController.shareSessionToken(sessionToken);
	}

	private void removeButtonsHighlight() {
		for (Node node : sidebarButtons.getChildren()) // for every button in vbox
			node.setStyle("-fx-font-weight: normal;"); // remove "highlight"
	}

	private void highlightPressedButton(Button pressedButton) {
		removeButtonsHighlight();
		pressedButton.setStyle("-fx-font-weight: bold;");
	}

	public PageController setContentArea(String string) {
		PageController pageController = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(string));
			borderPane.setCenter(loader.load());
			pageController = loader.getController();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
		return pageController;
	}

}
