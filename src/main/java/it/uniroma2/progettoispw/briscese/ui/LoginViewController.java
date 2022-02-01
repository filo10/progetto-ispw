package it.uniroma2.progettoispw.briscese.ui;

import it.uniroma2.progettoispw.briscese.bean.LoginBean;
import it.uniroma2.progettoispw.briscese.bean.NotificationBean;
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


	private void doLogin(int guiNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

			LoginBean beanToSend = new LoginBean(Integer.parseInt(usernameTextField.getText()), passwordField.getText());
			NotificationBean notificationBean = new NotificationBean();
			LoginBean receivedBean = controller.login(beanToSend, notificationBean);

			String role = receivedBean.getRole();

			if (guiNumber == 1) {
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
			}
			else if (guiNumber == 2) {
				switch (role) {
					case "passenger":
						fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/passenger/psngr-home.fxml"));
						break;
					case "driver":
						fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/driver/driver-home.fxml"));
						break;
					case "admin":
						fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/admin-home-view.fxml"));
						break;
					case "verifier":
						fxmlLoader.setLocation(this.getClass().getResource("/it/uniroma2/progettoispw/gui2/verifier/verifier-home.fxml"));
						break;
					default:
						throw new MyLoginException("No role was found for the users...\nTry again or contact Client Service");
				}
			}

			SessionToken sessionToken = new SessionToken(receivedBean.getUserId(), receivedBean.getFullName(), role);

			Stage secondWindow = new Stage();
			Scene scene = new Scene(fxmlLoader.load());
			secondWindow.setTitle("Ubergata");
			secondWindow.setScene(scene);

			//  passare un token sessione con i dati dell'user
			if (guiNumber == 1) {
				HomeViewController nextViewController = fxmlLoader.getController();
				nextViewController.setSessionToken(sessionToken);
				notificationBean.setViewController(nextViewController);
				nextViewController.setNotificationBean(notificationBean);
				nextViewController.injectSessionToken();
			}
			else if (guiNumber == 2) {
				MyViewController nextViewController = fxmlLoader.getController();
				nextViewController.setSessionToken(sessionToken);
				notificationBean.setViewController(nextViewController);
				nextViewController.setNotificationBean(notificationBean);
			}

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
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			errorLabel.setText("Username must be a number");
			errorLabel.setVisible(true);
		}
	}

	public void onLoginGui1() {
		doLogin(1);
	}

	public void onLoginGui2() {
		doLogin(2);
	}

	public void onSignupButtonClick() {
		try {
			Stage window = (Stage) usernameTextField.getScene().getWindow();
			Parent newSceneRes = FXMLLoader.load(getClass().getResource("/it/uniroma2/progettoispw/signup-view.fxml"));
			Scene newScene = new Scene(newSceneRes);
			window.setScene(newScene);
			window.show();
		} catch (IOException e) {
			alertDialogFXMLError(e.getCause().toString() + "\n" + e.getMessage());
		}
	}

	public void onCloseButtonClick() {
		Platform.exit();
	}

}