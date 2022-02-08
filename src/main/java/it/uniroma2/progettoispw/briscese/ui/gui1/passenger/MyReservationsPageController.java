package it.uniroma2.progettoispw.briscese.ui.gui1.passenger;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ReserveSeatController;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.ui.SessionToken;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class MyReservationsPageController extends PageController {
	@FXML private TreeView<String> treeView;
	TreeItem<String> dummyRoot = new TreeItem<>();
	@FXML private TextField rideidTF;
	@FXML private Button leaveButton;
	@FXML private Button cancelButton;
	private ReserveSeatController controller = new ReserveSeatController();


	@Override
	public void shareSessionToken(SessionToken token) {
		super.shareSessionToken(token);
		treeView.setRoot(dummyRoot);
		treeView.setShowRoot(false);
		treeView.setStyle("-fx-wrap-text: true;");
		initView();
	}

	private void initView() {
		try {
			treeView.getRoot().getChildren().clear();

			TreeItem<String> rootIncomingRides = new TreeItem<>("INCOMING RIDES");
			TreeItem<String> rootPastRides = new TreeItem<>("PAST RIDES");
			TreeItem<String> rootPendingRequests = new TreeItem<>("PENDING REQUESTS");
			rootIncomingRides.setExpanded(true);
			rootPastRides.setExpanded(false);
			rootPendingRequests.setExpanded(true);

			SeatRequestBean beanToSend = new SeatRequestBean(-1, sessionToken.getUserId());

			for (RideBean bean : controller.getMyReservedSeats(beanToSend)) {
				String item = String.format("Ride ID: %d  (on %s, %s)%nFrom: %s%nTo: %s",
						bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(), bean.getFinishPoint());
				if (LocalDate.now().compareTo(LocalDate.parse(bean.getDate())) > 0) {
					rootPastRides.getChildren().add(new TreeItem<>(item));
				} else {
					rootIncomingRides.getChildren().add(new TreeItem<>(item));
				}
			}

			for (RideBean bean : controller.getMyRequests(beanToSend)) {
				String item = String.format("Ride ID: %d  (on %s, %s)%nFrom: %s%nTo: %s",
						bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(), bean.getFinishPoint());
				rootPendingRequests.getChildren().add(new TreeItem<>(item));
			}

			dummyRoot.getChildren().add(rootIncomingRides);
			dummyRoot.getChildren().add(rootPastRides);
			dummyRoot.getChildren().add(rootPendingRequests);

		} catch (UserNotFoundException e) {
			Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			error.showAndWait();
		}
	}

	public void onLeaveCancelButtonClick(ActionEvent event) {
		try {
			int rideId = getTextFieldInt();
			if (rideId < 0)
				rideidTF.clear();

			if (event.getSource() == leaveButton) {
				controller.leaveRide(new SeatRequestBean(rideId, sessionToken.getUserId()));
				Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Ride left.", ButtonType.OK);
				confirmation.showAndWait();
			}

			if (event.getSource() == cancelButton) {
				controller.cancelRequest(new SeatRequestBean(rideId, sessionToken.getUserId()));
				Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Seat Request cancelled.", ButtonType.OK);
				confirmation.showAndWait();
			}

			initView();

		} catch (SeatRequestException e) {
			Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			error.showAndWait();
		}
	}

	private int getTextFieldInt() {
		try {
			return Integer.parseInt(rideidTF.getText());
		} catch (NumberFormatException e) {
			rideidTF.clear();
		}
		return -1;
	}
}
