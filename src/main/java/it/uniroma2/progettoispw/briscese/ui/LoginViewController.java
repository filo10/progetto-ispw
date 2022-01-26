package it.uniroma2.progettoispw.briscese.ui;

import it.uniroma2.progettoispw.briscese.bean.LoginBean;
import it.uniroma2.progettoispw.briscese.controller.LoginController;
import it.uniroma2.progettoispw.briscese.exceptions.MyLoginException;
import it.uniroma2.progettoispw.briscese.ui.gui1.HomeViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginViewController extends MyViewController {

	@FXML private TextField usernameTextField;
	@FXML private PasswordField passwordField;
	@FXML private Label errorLabel;
	private LoginController controller = new LoginController();


	public void onLoginButtonClick() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

			LoginBean beanToSend = new LoginBean(Integer.parseInt(usernameTextField.getText()), passwordField.getText());
			LoginBean receivedBean = controller.login(beanToSend);

			String role = receivedBean.getRole();

			switch (role) {
				case "passenger":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui1/passenger-home-view.fxml"));
					break;
					case "driver":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui1/driver-home-view.fxml"));
					break;
					case "admin":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui1/admin-home-view.fxml"));
					break;
				case "verifier":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui1/verifier-home-view.fxml"));
					break;
				default:
					throw new MyLoginException("No role was found for the users... Contact Client Service");
			}

			SessionToken sessionToken = new SessionToken(receivedBean.getUserId(), receivedBean.getFullName(), role);

			Stage secondWindow = new Stage();
			Scene scene = new Scene(fxmlLoader.load());
			secondWindow.setTitle("Ubergata");
			secondWindow.setScene(scene);

			//  passare un token sessione con i dati dell'user
			HomeViewController nextViewController = fxmlLoader.getController();
			nextViewController.setSessionToken(sessionToken);
			nextViewController.injectSessionToken();

			secondWindow.initStyle(StageStyle.UNDECORATED);
			secondWindow.show();

			usernameTextField.clear();
			passwordField.clear();

		} catch (MyLoginException e) {
			//Creating a dialog
			Dialog<String> dialog = new Dialog<>();
			//Adding buttons to the dialog pane
			ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(type);

			dialog.setTitle("Login failed");
			dialog.setContentText(e.getMessage());
			dialog.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			alertDialogMissingFXML();
		} catch (NumberFormatException e) {
			errorLabel.setText("Username must be a number");
			errorLabel.setVisible(true);
		}
	}

	public void onLoginGui2ButtonClick() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

			LoginBean beanToSend = new LoginBean(Integer.parseInt(usernameTextField.getText()), passwordField.getText());
			LoginBean receivedBean = controller.login(beanToSend);

			String role = receivedBean.getRole();

			switch (role) {
				case "passenger":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/psngr-home.fxml"));
					break;
				case "driver":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/driver-home-view.fxml"));
					break;
				case "admin":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/admin-home-view.fxml"));
					break;
				case "verifier":
					fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/verifier-home.fxml"));
					break;
				default:
					throw new MyLoginException("No role was found for the users...\nTry again or contact Client Service");
			}

			SessionToken sessionToken = new SessionToken(receivedBean.getUserId(), receivedBean.getFullName(), role);

			Stage secondWindow = new Stage();
			Scene scene = new Scene(fxmlLoader.load());
			secondWindow.setTitle("Ubergata");
			secondWindow.setScene(scene);

			MyViewController nextViewController = fxmlLoader.getController();
			nextViewController.setSessionToken(sessionToken);

			secondWindow.initStyle(StageStyle.UNDECORATED);
			secondWindow.show();

			usernameTextField.clear();
			passwordField.clear();

		} catch (MyLoginException e) {
			//Creating a dialog
			Dialog<String> dialog = new Dialog<>();
			//Adding buttons to the dialog pane
			ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(type);

			dialog.setTitle("Login failed");
			dialog.setContentText(e.getMessage());
			dialog.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			alertDialogMissingFXML();
		} catch (NumberFormatException e) {
			errorLabel.setText("Username must be a number");
			errorLabel.setVisible(true);
		}
	}

	public void onSignupButtonClick() {
		try {
			Stage window = (Stage) usernameTextField.getScene().getWindow();
			Parent newSceneRes = FXMLLoader.load(getClass().getResource("/it/uniroma2/progettoispw/signup-view.fxml"));
			Scene newScene = new Scene(newSceneRes);
			window.setScene(newScene);
			window.show();
		} catch (IOException e) {
			alertDialogMissingFXML();
		}
	}

	public void onCloseButtonClick() {
		Platform.exit();
	}
}
