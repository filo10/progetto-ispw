package it.uniroma2.progettoispw.briscese.ui.gui1.passenger;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.controller.ReserveSeatController;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.ui.gui1.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class RequestSeatPageController extends PageController {
	@FXML private TextField rideidTF;
	@FXML private ListView<String> listView;
	private ReserveSeatController contorller = new ReserveSeatController();


	@FXML
	public void initialize() {
		fillList();
	}

	@FXML
	void onSendButtonClick() {
		try {
			SeatRequestBean bean = new SeatRequestBean(Integer.parseInt(rideidTF.getText()), sessionToken.getUserId());
			contorller.sendRequest(bean);
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Request sent!", ButtonType.OK);
			alert.showAndWait();
		} catch (SeatRequestException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		} catch (NumberFormatException e) {
			rideidTF.clear();
		}
	}

	public void fillList() {
		List<RideBean> list = contorller.getAvailableRides();
		for (RideBean bean : list) {
			String item = String.format("- Ride ID: %d  on %s %s%n  Driver: %s%n  From: %s%n  To: %s",
					bean.getRideId(), bean.getDate(), bean.getTime(),
					bean.getDriverName(),
					bean.getStartPoint(),
					bean.getFinishPoint());

			listView.getItems().add(item);
		}
	}

}
