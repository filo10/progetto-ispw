package it.uniroma2.progettoispw.briscese.ui.gui1.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatReplyBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.RideManagementException;
import it.uniroma2.progettoispw.briscese.exceptions.RideNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.util.List;

public class RidePageController extends PageController {
	private Node previousNodeAtCenter;
	private ManageRideController controller = new ManageRideController();
	private List<SeatRequestBean> passengersList;
	private List<SeatRequestBean> requestsList;
	private int rideId;
	@FXML
	private Button acceptButton;
	@FXML
	private Button declineButton;
	@FXML
	private Label passengersLabel;
	@FXML
	private Label requestsLabel;
	@FXML
	private Label requestsTitle;
	@FXML
	private Label rideLabel;
	@FXML
	private TextField userIdTextField;


	public void goBack() {
		BorderPane borderPane = (BorderPane) rideLabel.getScene().getRoot();
		borderPane.setCenter(previousNodeAtCenter);
	}

	public void init(Node previousNodeAtCenter, int rideId) {
		this.previousNodeAtCenter = previousNodeAtCenter;
		this.rideId = rideId;

		showInfo();
	}

	private void showInfo() {
		try {
			// 	- prendi le info per la corsa rideid
			RideBean beanToSend = new RideBean();
			beanToSend.setRideId(rideId);
			RideBean bean = controller.getRide(beanToSend);
			String rideString = String.format("Ride #%d%nDatetime: %s, %s%nNumber of seats: %d%nFrom: %s%nTo: %s",
					bean.getRideId(), bean.getDate(), bean.getTime(), bean.getNumberOfSeats(), bean.getStartPoint(), bean.getFinishPoint());
			rideLabel.setText(rideString);

			//	- prendi i passeggeri
			passengersList = controller.getRidePassengers(beanToSend);
			for (SeatRequestBean srbean : passengersList) {
				String text = passengersLabel.getText() + srbean.getPassengerName() + " (userID: " + srbean.getPassengerId() + ")\n";
				passengersLabel.setText(text);
			}

			//	- prendi le richieste se la corsa è oggi o nel futuro
			if (LocalDate.now().compareTo(LocalDate.parse(bean.getDate())) <= 0) {
				requestsLabel.setVisible(true);
				requestsTitle.setVisible(true);
				acceptButton.setVisible(true);
				declineButton.setVisible(true);
				userIdTextField.setVisible(true);
				requestsList = controller.getRideSeatRequests(beanToSend);
				for (SeatRequestBean srbean : requestsList) {
					String text = requestsLabel.getText() + srbean.getPassengerName() + " (userID: " + srbean.getPassengerId() + ")\n";
					requestsLabel.setText(text);
				}
			}

		} catch (RideNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			goBack();
		}
	}

	public void onAcceptClick() {
		try {
			if (userIdTextField.getText().isBlank())
				return;
			int passengerId = checkTextFieldIsInt();

			SeatReplyBean beanToSend = new SeatReplyBean(rideId, passengerId, true);
			controller.replySeatRequest(beanToSend);
			// refresh lists TODO serve tenere memoria dei beans?
			passengersLabel.setText("");
			requestsLabel.setText("");
			showInfo();

		} catch (RideManagementException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	// TODO rifiuta richiesta (refresh liste)

	// TODO elimina passeggero (refresh liste)

	private int checkTextFieldIsInt() {
		int integer = -1;
		try {
			integer = Integer.parseInt(userIdTextField.getText());
			if (integer < 0)
				throw new NumberFormatException();
			return integer;
		} catch (NumberFormatException e) {
			userIdTextField.clear();
		}
		return integer;
	}
}
