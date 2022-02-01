package it.uniroma2.progettoispw.briscese.ui;

import it.uniroma2.progettoispw.briscese.bean.LoginBean;
import it.uniroma2.progettoispw.briscese.bean.NotificationBean;
import it.uniroma2.progettoispw.briscese.controller.LoginController;
import it.uniroma2.progettoispw.briscese.exceptions.MyLoginException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
		dialog.setTitle("ERROR");
		Text text = new Text(message);
		text.setWrappingWidth(300);
		dialog.getDialogPane().setContent(text);
		dialog.getDialogPane().setPadding(new Insets(8,8,8,8));

		dialog.showAndWait();
	}

	public void newNotification(String text) {
		// Do nothing. The sub-class can @Override this method to implement its desired behaviour.
	}

	protected void setNotificationBean(NotificationBean notificationBean) {
		this.notificationBean = notificationBean;
	}

	public void askControllerToLogout() {
		LoginController controller = new LoginController();
		LoginBean loginBean = new LoginBean(sessionToken.getUserId(), "");
		try {
			controller.logout(loginBean, notificationBean);
		} catch (MyLoginException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
