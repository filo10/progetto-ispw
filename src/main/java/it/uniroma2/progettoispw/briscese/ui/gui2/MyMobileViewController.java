package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class MyMobileViewController extends MyViewController {
	protected Scene previousScene;

	protected void nextView(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Button pressedButton = (Button) event.getSource();
			String resName = pressedButton.getUserData().toString();
			loader.setLocation(getClass().getResource(resName));
			Stage window = (Stage) pressedButton.getScene().getWindow();
			Scene newScene = new Scene(loader.load());
			window.setScene(newScene);

			MyViewController controller = loader.getController();
			controller.setSessionToken(sessionToken);

			window.show();
		} catch (IOException | IllegalStateException e) {
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
		}
	}

	protected MyMobileViewController nextViewAndRememberThisScene(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Button pressedButton = (Button) event.getSource();
			Scene thisScene = pressedButton.getScene();

			String resName = pressedButton.getUserData().toString();
			loader.setLocation(getClass().getResource(resName));

			Stage window = (Stage) thisScene.getWindow();
			Scene newScene = new Scene(loader.load());
			window.setScene(newScene);

			MyMobileViewController controller = loader.getController();
			controller.setSessionToken(sessionToken);
			controller.setPreviousScene(thisScene);

			window.show();
			return controller;

		} catch (IOException | IllegalStateException e) {
			alertDialogFXMLError("something went wrong...\n\n" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	protected MyMobileViewController nextViewAndRememberThisScene(String resourceName, Scene scene) {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource(resourceName));

			Stage window = (Stage) scene.getWindow();
			Scene newScene = new Scene(loader.load());
			window.setScene(newScene);

			MyMobileViewController controller = loader.getController();
			controller.setSessionToken(sessionToken);
			controller.setPreviousScene(scene);

			window.show();
			return controller;

		} catch (IOException | IllegalStateException e) {
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
		}
		return null;
	}

	protected void setPreviousScene(Scene scene) {
		this.previousScene = scene;
	}

	protected void goToPreviusView(ActionEvent event){
		try {
			Button pressedButton = (Button) event.getSource();

			Scene thisScene = pressedButton.getScene();

			Stage window = (Stage) thisScene.getWindow();

			window.setScene(previousScene);

			window.show();

		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public void onLogoutButtonClick(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();
		Stage window = (Stage) pressedButton.getScene().getWindow();
		window.close();
		askControllerToLogout();
	}
}
