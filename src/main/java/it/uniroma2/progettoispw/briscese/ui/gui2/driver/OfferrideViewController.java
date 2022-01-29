package it.uniroma2.progettoispw.briscese.ui.gui2.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.ManageRideException;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OfferrideViewController extends MyMobileViewController {
	@FXML private Spinner<Integer> seatsSpinner;
	@FXML private DatePicker datePicker;
	@FXML private TextField hourTF;
	@FXML private TextField minTF;
	@FXML private TextField startTF;
	@FXML private TextField finishTF;
	@FXML private Button offerButton;
	@FXML private Label label;
	private ManageRideController controller;


	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	public void onOfferButtonClick() {
		if (!checkFieldsAreValid())
			return;

		RideBean bean = new RideBean();
		bean.setDriverId(sessionToken.getUserId());
		bean.setNumberOfSeats(seatsSpinner.getValue());
		bean.setDate(datePicker.getValue().toString());
		bean.setTime(getTimeFromFields());
		bean.setStartPoint(startTF.getText());
		bean.setFinishPoint(finishTF.getText());

		label.setText("");

		controller = new ManageRideController();
		try {
			controller.offerRide(bean);
			offerButton.setDisable(true);
			label.setText("Success!");
		} catch (ManageRideException e) {
			label.setText(e.getMessage());
		}
	}

	private boolean checkFieldsAreValid() {
		if (datePicker.getValue() == null) {
			label.setText("Date cannot be empty.");
			return false;
		}
		if (getTimeFromFields() == null)
			return false;
		if (startTF.getText().isBlank() || finishTF.getText().isBlank()) {
			label.setText("'From' and 'To' fields cannot be empty.");
			return false;
		}

		return true;
	}

	private String getTimeFromFields() {
		try {
			String str = hourTF.getText() + ":" + minTF.getText();
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
			LocalTime time = LocalTime.parse(str, formatter);
			return time.toString();
		} catch (DateTimeParseException e) {
			label.setText("Hour and Minutes must be valid numbers.");
			return null;
		}
	}

	@FXML
	public void initialize() {
		initSpinner();
	}

	public void initSpinner() {
		seatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,8));
	}

}
