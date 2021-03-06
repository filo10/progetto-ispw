package it.uniroma2.progettoispw.briscese.ui.gui1;

import it.uniroma2.progettoispw.briscese.ui.MyViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeViewController extends MyViewController {
	@FXML protected Button notificationsButton;
	@FXML protected VBox sidebarButtons;
	@FXML private Label nameLabel;
	@FXML private Label roleLabel;
	@FXML private BorderPane borderPane;
	private int unreadNotifications = 0;
	private List<String> notifications = new ArrayList<>();

	public void injectSessionToken() {
		nameLabel.setText(sessionToken.getName() + "\n[userID: " + sessionToken.getUserId() + "]");
		roleLabel.setText(sessionToken.getRole());
	}

	public void logout(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();
		Stage stage = (Stage) pressedButton.getScene().getWindow();
		stage.close();
		askControllerToLogout();
	}

	public void onAPageButtonClick(ActionEvent event) {
		Button pressedButton = (Button) event.getSource();
		highlightPressedButton(pressedButton);
		PageController pageController = setContentArea(pressedButton.getUserData().toString()); // the name of the fxml file to open is inside button's user data attribute
		pageController.shareSessionToken(sessionToken);
	}

	private void removeButtonsHighlight() {
		for (Node node : sidebarButtons.getChildren()) // for every button in vbox
			node.setStyle("-fx-font-weight: normal;"); // remove "highlight"
	}

	private void highlightPressedButton(Button pressedButton) {
		removeButtonsHighlight();
		pressedButton.setStyle("-fx-font-weight: bold;");
	}

	public PageController setContentArea(String string) {
		PageController pageController = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(string));
			Node node = loader.load();
			borderPane.setCenter(node);
			pageController = loader.getController();
		} catch (IOException e) {
			alertDialogFXMLError("Something went wrong...\n" + e.getMessage());
		}
		return pageController;
	}

	@Override
	public void newNotification(String text) {
		unreadNotifications++;
		notifications.add(text);
		notificationsButton.setText("Notifications (" + unreadNotifications +")");
	}

	public void onNotificationsButtonClick(ActionEvent event) {
		notificationsButton.setText("Notifications");
		Button pressedButton = (Button) event.getSource();
		highlightPressedButton(pressedButton);

		// crea una tree view con due sezioni LETTE e NON LETTE
		TreeItem<String> dummyRoot = new TreeItem<> (); // this is used to make it like treeview has 2 root nodes
		TreeItem<String> rootRead = new TreeItem<> ("READ");
		TreeItem<String> rootUnread = new TreeItem<> ("UNREAD");
		rootRead.setExpanded(false);
		rootUnread.setExpanded(true);

		int i = 0;

		//fill read notifications
		for (; i < notifications.size() - unreadNotifications; i++) {
			TreeItem<String> item = new TreeItem<> (notifications.get(i));
			rootRead.getChildren().add(item);
		}
		// fill unread notifications
		for (; i < notifications.size(); i++) {
			TreeItem<String> item = new TreeItem<> (notifications.get(i));
			rootUnread.getChildren().add(item);
		}

		dummyRoot.getChildren().add(rootRead);
		dummyRoot.getChildren().add(rootUnread);
		TreeView<String> tree = new TreeView<> (dummyRoot);
		tree.setShowRoot(false);
		tree.setStyle("-fx-wrap-text: true;");

		AnchorPane anchorPane = new AnchorPane(tree);
		AnchorPane.setTopAnchor(tree, 8.0);
		AnchorPane.setBottomAnchor(tree, 8.0);
		AnchorPane.setLeftAnchor(tree, 8.0);
		AnchorPane.setRightAnchor(tree, 8.0);

		tree.setCellFactory(item -> {
			TreeCell<String> treeCell = new TreeCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty)
						setText(item);
					else
						setText("");
				}
			};
			treeCell.setStyle("-fx-wrap-text: true;");
			treeCell.prefWidthProperty().bind(tree.widthProperty().subtract(20.0));
			return treeCell;
		});

		borderPane.setCenter(anchorPane);
		unreadNotifications = 0;
	}
}
