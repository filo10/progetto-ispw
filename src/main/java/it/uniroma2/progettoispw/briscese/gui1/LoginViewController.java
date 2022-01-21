package it.uniroma2.progettoispw.briscese.gui1;

import it.uniroma2.progettoispw.briscese.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

	@FXML private TextField usernameTextField;
	@FXML private PasswordField passwordField;
	@FXML private Label errorLabel;
	private LoginController controller = new LoginController();


	public void onLoginButtonClick() throws Exception, IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();

		switch (controller.login(Integer.parseInt(usernameTextField.getText()), passwordField.getText())) {
			case "passenger":
				fxmlLoader.setLocation(this.getClass().getResource("/gui1/passenger-home-view.fxml"));
				break;
			// TODO case driver, admin
			case "verifier":
				fxmlLoader.setLocation(this.getClass().getResource("/gui1/verifier-home-view.fxml"));
				break;
			default: // TODO sostituisci con exception
				errorLabel.setText("something went wrong. try again");
		}

		Stage secondWindow = new Stage();
		Scene scene = new Scene(fxmlLoader.load());
		secondWindow.setTitle("Ubergata");
		secondWindow.setScene(scene);

		// TODO passare un token sessione con i dati dell'user (matricola, nome, ruolo)

		secondWindow.show();

		usernameTextField.clear();
		passwordField.clear();
	}

	public void onSignupButtonClick() {
		// apri un dialog


		// ottieni matricola, nome+cognome, password, ripetipassword

		// chiama il controller

		// chiudi dialog e di di fare login
	}
}
