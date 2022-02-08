package it.uniroma2.progettoispw.briscese.ui.gui2.passenger;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ReserveSeatController;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyReservationsViewController extends MyMobileViewController {
	@FXML
	private ListView<String> listView;
	@FXML private TextField textField;
	@FXML private Button incomingButton;
	@FXML private Button pastButton;
	@FXML private Button pendingButton;
	@FXML private Button leaveButton;
	@FXML private Button cancelButton;
	private List<RideBean> pastRides = new ArrayList<>();
	private List<RideBean> futureRides = new ArrayList<>();
	private List<RideBean> requests = new ArrayList<>();
	private ReserveSeatController controller = new ReserveSeatController();
	private static final String NORMAL_STYLE = "-fx-font-weight: normal;";


	@Override
	public void setPreviousScene(Scene scene) {
		super.setPreviousScene(scene);
		getBeans();
		fillListView(futureRides);
	}

	private void getBeans() {
		try {
			requests.clear();
			pastRides.clear();
			futureRides.clear();

			SeatRequestBean beanToSend = new SeatRequestBean(-1, sessionToken.getUserId());
			requests = controller.getMyRequests(beanToSend);

			for (RideBean bean : controller.getMyReservedSeats(beanToSend)) {
				if (LocalDate.now().compareTo(LocalDate.parse(bean.getDate())) > 0) {
					pastRides.add(bean);
				} else {
					futureRides.add(bean);
				}
			}

		} catch (UserNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	private void fillListView(List<RideBean> list) {
		listView.getItems().clear();

		for (RideBean bean : list){
			String item = String.format("Ride ID: %d  (on %s, %s)%nFrom: %s%nTo: %s",
					bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(), bean.getFinishPoint());
			listView.getItems().add(item);
		}
	}

	public void onListsButtonsClick(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();

		String style = NORMAL_STYLE;
		incomingButton.setStyle(style);
		pastButton.setStyle(style);
		pendingButton.setStyle(style);

		getBeans();

		if (pressedButton == incomingButton) {
			fillListView(futureRides);
		} else if (pressedButton == pastButton) {
			fillListView(pastRides);
		} else if (pressedButton == pendingButton) {
			fillListView(requests);
		}
		pressedButton.setStyle("-fx-font-weight: bold;");
	}

	@FXML
	void onLeaveCancelButtonClick(ActionEvent event) {
		try {
			int rideId = Integer.parseInt(textField.getText());
			if (rideId < 0)
				textField.clear();

			SeatRequestBean bean = new SeatRequestBean(rideId, sessionToken.getUserId());

			String message = "";
			if (event.getSource() == leaveButton) {
				controller.leaveRide(bean);
				message = "Ride left!";
			}
			else if (event.getSource() == cancelButton) {
				controller.cancelRequest(bean);
				message = "Seat request cancelled!";
			}

			Alert success = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
			success.showAndWait();

			getBeans();
			fillListView(futureRides);

			incomingButton.setStyle("-fx-font-weight: bold;");
			pastButton.setStyle(NORMAL_STYLE);
			pendingButton.setStyle(NORMAL_STYLE);

		} catch (NumberFormatException e) {
			textField.clear();
		} catch (SeatRequestException e) {
			Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			error.showAndWait();
		}
	}

	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}
}
