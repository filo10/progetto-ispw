package it.uniroma2.progettoispw.briscese.ui.gui1.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatReplyBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.RideManagementException;
import it.uniroma2.progettoispw.briscese.exceptions.RideNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.util.List;

public class RidePageController extends PageController {
	private Node previousNodeAtCenter;
	private ManageRideController controller = new ManageRideController();
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
			// 	save rideId and show ride info
			RideBean beanToSend = new RideBean();
			beanToSend.setRideId(rideId);
			RideBean bean = controller.getRide(beanToSend);
			String rideString = String.format("Ride #%d%nDatetime: %s, %s%nNumber of seats: %d%nFrom: %s%nTo: %s",
					bean.getRideId(), bean.getDate(), bean.getTime(), bean.getNumberOfSeats(), bean.getStartPoint(), bean.getFinishPoint());
			rideLabel.setText(rideString);

			//	get passengers
			List<SeatRequestBean> passengersList = controller.getRidePassengers(beanToSend);
			for (SeatRequestBean srbean : passengersList) {
				String text = passengersLabel.getText() + srbean.getPassengerName() + " (userID: " + srbean.getPassengerId() + ")\n";
				passengersLabel.setText(text);
			}

			//	if ride is not in the past, get requests
			if (LocalDate.now().compareTo(LocalDate.parse(bean.getDate())) <= 0) {
				requestsLabel.setVisible(true);
				requestsTitle.setVisible(true);
				acceptButton.setVisible(true);
				declineButton.setVisible(true);
				userIdTextField.setVisible(true);
				List<SeatRequestBean> requestantsList = controller.getRideSeatRequests(beanToSend);
				for (SeatRequestBean srbean : requestantsList) {
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

	public void onAcceptDeclineClick(ActionEvent event) {
		try {
			boolean response;
			if (event.getSource() == acceptButton) {
				response = true;
			} else if (event.getSource() == declineButton) {
				response = false;
			} else {
				return;
			}

			if (userIdTextField.getText().isBlank())
				return;
			int passengerId = checkTextFieldIsInt();

			SeatReplyBean beanToSend = new SeatReplyBean(rideId, passengerId, response);
			controller.replySeatRequest(beanToSend);

			passengersLabel.setText("");
			requestsLabel.setText("");
			showInfo();

		} catch (RideManagementException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void onRemoveClick() {
		try {
			if (userIdTextField.getText().isBlank())
				return;
			int passengerId = checkTextFieldIsInt();

			SeatReplyBean beanToSend = new SeatReplyBean(rideId, passengerId, false);
			controller.removePassenger(beanToSend);

			passengersLabel.setText("");
			requestsLabel.setText("");
			showInfo();

		} catch (RideManagementException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

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

	// TODO delete ride

}
