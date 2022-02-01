package it.uniroma2.progettoispw.briscese.ui;

import it.uniroma2.progettoispw.briscese.bean.LoginBean;
import it.uniroma2.progettoispw.briscese.controller.LoginController;
import it.uniroma2.progettoispw.briscese.exceptions.SignupException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupViewController extends MyViewController{
	@FXML private TextField enrollNumberTextField;
	@FXML private TextField nameTextField;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField confirmPasswordField;
	@FXML private Label errorLabel;
	@FXML private Button signupButton;


	public void onSignupButtonClick() {
		//Creating a dialog
		Dialog<String> dialog = new Dialog<>();
		//Adding buttons to the dialog pane
		ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);

		try {
			LoginController controller = new LoginController();
			int enrollNumber = Integer.parseInt(enrollNumberTextField.getText());
			LoginBean beanToSend = new LoginBean(enrollNumber, passwordField.getText());
			beanToSend.setFullName(nameTextField.getText());

			controller.signup(beanToSend);

			signupButton.setDisable(true);

			//Setting the title
			dialog.setTitle("Success!");
			//Setting the content of the dialog
			dialog.setContentText("Congratulations you're now a new Ubergata user!\nPlease go back and log in.");
			//Show dialog
			dialog.showAndWait();
		} catch (SignupException e) {
			dialog.setTitle("Signup failed");
			dialog.setContentText(e.getMessage());
			dialog.showAndWait();
		}
	}

	public void onBackButtonClick() {
		try {
			Stage window = (Stage) nameTextField.getScene().getWindow();
			Parent newSceneRes = null;
			newSceneRes = FXMLLoader.load(getClass().getResource("/it/uniroma2/progettoispw/login-view.fxml"));
			Scene newScene = new Scene(newSceneRes);
			window.setScene(newScene);
			window.show();
		} catch (IOException e) {
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
		}
	}

	public void checkPasswordFields() {
		if (passwordField.getText().equals(confirmPasswordField.getText()) && !passwordField.getText().isBlank()) {
			errorLabel.setVisible(false);
			if ((!enrollNumberTextField.getText().isBlank() && !nameTextField.getText().isBlank()) && enrollNumberTextField.getText().matches("\\d+"))
				signupButton.setDisable(false);
		} else {
			errorLabel.setVisible(true);
			signupButton.setDisable(true);
		}
	}

	public void checkNotEmpty() {
		if (enrollNumberTextField.getText().isBlank() || nameTextField.getText().isBlank()) {
			signupButton.setDisable(true);
		}
		if (!enrollNumberTextField.getText().isBlank() && !nameTextField.getText().isBlank()) {
			checkPasswordFields();
		}
	}
}
