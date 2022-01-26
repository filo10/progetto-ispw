package it.uniroma2.progettoispw.briscese.ui;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class MyViewController {
	protected SessionToken sessionToken;
	protected double xOffset = 0;
	protected double yOffset = 0;

	public void setSessionToken(SessionToken sessionToken) {
		this.sessionToken = sessionToken;
	}

	public void onMousePressed(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	public void onMouseDragged(MouseEvent event) {
		Node node = (Node) event.getSource();
		Stage thisWindow = (Stage) node.getScene().getWindow();

		thisWindow.setX(event.getScreenX() - xOffset);
		thisWindow.setY(event.getScreenY() - yOffset);
	}

	public void alertDialogMissingFXML() {
		//Creating a dialog
		Dialog<String> dialog = new Dialog<>();
		//Adding buttons to the dialog pane
		ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);

		dialog.setTitle("CRITICAL ERROR");
		dialog.setContentText("Cannot load user interface.\nPlease reinstall the application.");
		dialog.showAndWait();
	}
}
