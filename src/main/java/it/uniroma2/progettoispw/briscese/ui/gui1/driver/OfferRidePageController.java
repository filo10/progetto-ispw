package it.uniroma2.progettoispw.briscese.ui.gui1.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.ManageRideException;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class OfferRidePageController extends PageController {
	@FXML private TextField seatsTextField;
	@FXML private TextField dateTextField;
	@FXML private TextField timeTextField;
	@FXML private TextField startTextField;
	@FXML private TextField finishTextField;
	@FXML private Label label;
	@FXML private Button offerButton;


	public void onOfferButtonClick() {
		try {
			// controlla che il num posti sia un int tra 1 e 8.
			int numberOfSeats = Integer.parseInt(seatsTextField.getText());
			if (!(numberOfSeats > 0 && numberOfSeats < 9)) {
				label.setText("Number of seats must be between 1 and 8.");
				return;
			}
			// controlla che la data sia valida
			if (!dateIsValid(dateTextField.getText())) {
				label.setText("Date must be valid and in YYYY-MM-DD format.");
				return;
			}
			// controlla che l'orario sia valido
			if (!timeIsValid(timeTextField.getText())) {
				label.setText("Time must be valid and in HH:MM format.");
				return;
			}
			// controlla che gli indirizzi non sono null
			if (startTextField.getText().isBlank() || finishTextField.getText().isBlank()) {
				label.setText("'From' and 'To' fields cannot be empty.");
				return;
			}

			// offri corsa
			RideBean bean = new RideBean();
			bean.setDriverId(sessionToken.getUserId());
			bean.setNumberOfSeats(numberOfSeats);
			bean.setDate(dateTextField.getText());
			bean.setTime(timeTextField.getText());
			bean.setStartPoint(startTextField.getText());
			bean.setFinishPoint(finishTextField.getText());

			ManageRideController logicController = new ManageRideController();
			logicController.offerRide(bean);
			label.setText("Success!");
			offerButton.setDisable(true);
		} catch (ManageRideException e) {
			label.setText(e.getMessage());
		} catch (NumberFormatException e) {
			label.setText("Number of seats must be a number!");
		}
	}

	private boolean dateIsValid(String date) {
		try {
			LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	private boolean timeIsValid(String time) {
		try {
			LocalTime.parse(time);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
