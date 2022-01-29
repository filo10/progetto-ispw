package it.uniroma2.progettoispw.briscese.bean;

import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import it.uniroma2.progettoispw.briscese.ui.gui1.HomeViewController;

public class NotificationBean {
	private MyViewController viewController;

	public void alert(String message) {
		viewController.newNotification(message);
	}

	public void setViewController(MyViewController viewController) {
		this.viewController = viewController;
	}
}
