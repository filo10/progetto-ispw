package it.uniroma2.progettoispw.briscese.ui.gui1.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.exceptions.RideManagementException;
import it.uniroma2.progettoispw.briscese.ui.SessionToken;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MyRidesPageController extends PageController {
	@FXML private ListView<String> nextListView;
	@FXML private ListView<String> pastListView;
	@FXML private TextField rideidTF;
	private ManageRideController controller = new ManageRideController();

	@FXML
	void onViewButtonClick() {
		try {
			int rideId = checkTextFieldIsIntAndGetValue();
			if (rideId < 0)
				return;

			BorderPane borderPane = (BorderPane) rideidTF.getScene().getRoot();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/it/uniroma2/progettoispw/gui1/pages/ride-page.fxml"));
			Node node = loader.load();
			RidePageController nextPageController = loader.getController();
			nextPageController.init(borderPane.getCenter(), rideId);
			borderPane.setCenter(node);

		} catch (NumberFormatException e) {
			rideidTF.clear();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.toString(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	@Override
	public void shareSessionToken(SessionToken token) {
		super.shareSessionToken(token);
		fillList();
	}

	public void fillList() {
		RideBean beanToSend = new RideBean();
		beanToSend.setDriverId(sessionToken.getUserId());
		List<RideBean> myRidesList = controller.getMyRides(beanToSend);

		for (RideBean bean : myRidesList) {
			int compare = LocalDate.now().compareTo(LocalDate.parse(bean.getDate()));
			String item = String.format("Ride ID: %d  (on %s, %s)%nFrom: %s%nTo: %s",
					bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(),	bean.getFinishPoint());
			if (compare <= 0) { // ride is today or in the future
				nextListView.getItems().add(item);
			} else { // ride is in the past
				pastListView.getItems().add(item);
			}
		}
	}

	public void onDeleteRideClick() {
		try {
			int rideId = checkTextFieldIsIntAndGetValue();
			if (rideId < 0)
				return;

			RideBean beanToSend = new RideBean();
			beanToSend.setRideId(rideId);
			controller.cancelRide(beanToSend);

		} catch (RideManagementException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	private int checkTextFieldIsIntAndGetValue() {
		int rideId = -1;
		try {
			rideId = Integer.parseInt(rideidTF.getText());
			if (rideId < 0) {
				rideidTF.clear();
				return rideId;
			}
		} catch (NumberFormatException e) {
			rideidTF.clear();
		}
		return rideId;
	}
}
