package it.uniroma2.progettoispw.briscese.ui.gui2;

import it.uniroma2.progettoispw.briscese.ui.gui2.passenger.NotificationsViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


public class MyMobileHomeViewController extends MyMobileViewController {
	@FXML private Label label;
	@FXML private Button notificationsButton;
	private int unreadNotifications = 0;
	private List<String> notifications = new ArrayList<>();

	public void onNavigationButtonClick(ActionEvent event) {
		nextViewAndRememberThisScene(event);
	}

	public void onNotificationsButtonClick(ActionEvent event) {
		NotificationsViewController nextViewController = (NotificationsViewController) nextViewAndRememberThisScene(event);
		nextViewController.shareNotifications(unreadNotifications, notifications);
		nextViewController.fillListView();
		unreadNotifications = 0;
		notificationsButton.setText("Notifications");
	}

	@Override
	public void newNotification(String text) {
		unreadNotifications++;
		notifications.add(text);
		notificationsButton.setText("Notifications (" + unreadNotifications + ")");
	}

}
