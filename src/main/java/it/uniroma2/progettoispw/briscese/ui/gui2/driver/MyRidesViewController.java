package it.uniroma2.progettoispw.briscese.ui.gui2.driver;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.controller.ManageRideController;
import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class MyRidesViewController extends MyMobileViewController {
	@FXML private ListView<String> ridesListView;
	@FXML private TextField textField;


	public void onViewButtonClick(ActionEvent event) {
		try {
			int rideId = Integer.parseInt(textField.getText());
			if (rideId < 0) {
				textField.clear();
				return;
			}
			RideViewController nextController = (RideViewController) nextViewAndRememberThisScene(event);
			nextController.shareRideInfo(rideId);
		} catch (NumberFormatException e) {
			textField.clear();
		}
	}

	@Override
	protected void setPreviousScene(Scene scene) {
		super.setPreviousScene(scene);
		initView();
	}

	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	public void initView() {
		ManageRideController controller = new ManageRideController();
		RideBean myUserIdBean = new RideBean();
		myUserIdBean.setDriverId(sessionToken.getUserId());
		List<RideBean> myRides = controller.getMyRides(myUserIdBean);

		ridesListView.getItems().add("### NEXT RIDES ###");
		for (RideBean bean : myRides) {
			LocalDate rideDate = LocalDate.parse(bean.getDate());
			int compare = LocalDate.now().compareTo(rideDate);
			if (compare <= 0) { // ride is today or in the future
				String str = String.format("#%d - %s at %s%nFrom: %s%nTo: %s", bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(), bean.getFinishPoint());
				ridesListView.getItems().add(str);
			}
		}
		ridesListView.getItems().add("");
		ridesListView.getItems().add("### OLD RIDES ###");
		for (RideBean bean : myRides) {
			LocalDate rideDate = LocalDate.parse(bean.getDate());
			int compare = LocalDate.now().compareTo(rideDate);
			if (compare > 0) {
				String str = String.format("#%d - %s at %s%nFrom: %s%nTo: %s", bean.getRideId(), bean.getDate(), bean.getTime(), bean.getStartPoint(), bean.getFinishPoint());
				ridesListView.getItems().add(str);
			}
		}
		setListViewItemsWrapText(ridesListView);
	}

	private void setListViewItemsWrapText(ListView<String> listView) {
		// wrap text of listview items
		listView.setCellFactory(param -> new ListCell<>(){
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item==null) {
					setGraphic(null);
					setText(null);
				}else{
					// set the width's
					setMinWidth(param.getWidth() - 20);
					setMaxWidth(param.getWidth() - 20);
					setPrefWidth(param.getWidth() - 20);
					// allow wrapping
					setWrapText(true);
					setText(item);
				}
			}
		});
	}

}
