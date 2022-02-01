package it.uniroma2.progettoispw.briscese.ui.gui2.passenger;

import it.uniroma2.progettoispw.briscese.ui.gui2.MyMobileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewController extends MyMobileViewController {
	@FXML ListView<String> listView;
	private int unread = 0;
	private List<String> notifList = new ArrayList<>();


	public void onBackButtonClick(ActionEvent event) {
		goToPreviusView(event);
	}

	public void shareNotifications(int unreadNotifications, List<String> notifications) {
		this.notifList = notifications;
		this.unread = unreadNotifications;
	}

	public void fillListView() {
		listView.getItems().clear();

		int readNotifications = notifList.size() - unread;

		listView.getItems().add("### UNREAD ###");
		for (int i = readNotifications; i < notifList.size(); i++) {
			listView.getItems().add(notifList.get(i));
		}
		listView.getItems().add("");
		listView.getItems().add("### OLD NOTIFICATIONS ###");
		for (int i = 0; i < readNotifications; i++) {
			listView.getItems().add(notifList.get(i));
		}

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
					setMinWidth(param.getWidth());
					setMaxWidth(param.getWidth());
					setPrefWidth(param.getWidth());
					// allow wrapping
					setWrapText(true);
					setText(item);
				}
			}
		});

	}

}
