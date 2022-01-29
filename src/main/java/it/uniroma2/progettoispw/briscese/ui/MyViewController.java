package it.uniroma2.progettoispw.briscese.ui;

import it.uniroma2.progettoispw.briscese.bean.NotificationBean;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class MyViewController {
	protected SessionToken sessionToken;
	protected NotificationBean notificationBean;
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

	public void alertDialogFXMLError(String message) {
		//Creating a dialog
		Dialog<String> dialog = new Dialog<>();
		//Adding buttons to the dialog pane
		ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);

		dialog.setTitle("CRITICAL ERROR");
		dialog.setContentText("Cannot load user interface:\n"+message);
		dialog.showAndWait();
	}

	public void newNotification(String text) {
		// Do nothing. The sub-class can @Override this method to implement its desired behaviour.
	}

	protected void setNotificationBean(NotificationBean notificationBean) {
		this.notificationBean = notificationBean;
	}
}
