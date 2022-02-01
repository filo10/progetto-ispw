package it.uniroma2.progettoispw.briscese.ui.gui2.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.RideNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class RideViewController extends MyMobileViewController {
	@FXML private Label datetimeLabel;
	@FXML private Label fromLabel;
	@FXML private Label numseatsLabel;
	@FXML private Label radeidLabel;
	@FXML private Label toLabel;
	@FXML private Label reqLabel;
	@FXML private TextField textField;
	@FXML private Button acceptButton;
	@FXML private Button removeButton;
	@FXML private ListView<String> passengersListView;
	@FXML private ListView<String> requestsListView;
	private ManageRideController controller = new ManageRideController();


	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	public void shareRideInfo(int rideId) {
		try {
			RideBean beanToSend = new RideBean();
			beanToSend.setRideId(rideId);
			RideBean bean = controller.getRide(beanToSend);
			radeidLabel.setText(bean.getRideId() + "");
			datetimeLabel.setText(bean.getDate() + " at " + bean.getTime());
			numseatsLabel.setText(bean.getNumberOfSeats() + "");
			fromLabel.setText(bean.getStartPoint());
			toLabel.setText(bean.getFinishPoint());

			for (SeatRequestBean passenger : controller.getRidePassengers(beanToSend)) {
				String str = String.format("%s (%s)", passenger.getPassengerName(), passenger.getPassengerId());
				passengersListView.getItems().add(str);
			}

			if (LocalDate.now().compareTo(LocalDate.parse(bean.getDate())) > 0) { // ride in the past
				reqLabel.setVisible(false);
				requestsListView.setVisible(false);
				textField.setVisible(false);
				acceptButton.setVisible(false);
				removeButton.setVisible(false);
			} else { // ride today or in the future
				for (SeatRequestBean requestant : controller.getRideSeatRequests(beanToSend)) {
					String str = String.format("%s (%s)", requestant.getPassengerName(), requestant.getPassengerId());
					requestsListView.getItems().add(str);
				}
			}

		} catch (RideNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			// TODO disabilita tasti accetta rimuovi
		}
	}

	// TODO rispondi richiesta
	// rimuovi passeggero
}
