package it.uniroma2.progettoispw.briscese.ui.gui2.passenger;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ReserveSeatController;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class RequestSeatViewController extends MyMobileViewController {
	@FXML private ListView<String> listView;
	@FXML private TextField textField;
	@FXML private Button sendButton;
	private ReserveSeatController controller = new ReserveSeatController();

	@FXML
	public void initialize() {
		fillListView();
	}

	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	public void onSendButtonClick() {
		try {
			int rideId = Integer.parseInt(textField.getText());
			if (rideId < 0)
				return;
			controller.sendRequest(new SeatRequestBean(rideId, sessionToken.getUserId()));
			sendButton.setDisable(true);
		} catch (NumberFormatException e) {
			textField.clear();
		} catch (SeatRequestException e) {
			Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			error.showAndWait();
		}
	}

	public void fillListView() {

		List<RideBean> resultsList = controller.getAvailableRides();

		for (RideBean rideBean : resultsList) {
			String string = String.format("- Ride ID: %d  on %s %s%n  Driver: %s%n  From: %s%n  To: %s",
					rideBean.getRideId(), rideBean.getDate(), rideBean.getTime(), rideBean.getDriverName(), rideBean.getStartPoint(), rideBean.getFinishPoint());
			listView.getItems().add(string);
		}

		listView.setCellFactory(param -> new ListCell<>(){
			@Override
			protected void updateItem(String listItem, boolean isEmpty) {
				super.updateItem(listItem, isEmpty);
				if (listItem != null && !isEmpty) {
					setText(listItem);
					setWrapText(true);
				} else {
					setText(null);
				}
			}
		});

	}
}
