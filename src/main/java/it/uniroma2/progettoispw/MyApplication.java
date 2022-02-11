package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.briscese.AppStateManager;
import it.uniroma2.progettoispw.briscese.exceptions.AppStateException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class MyApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		//remove window decoration
		stage.initStyle(StageStyle.UNDECORATED);

		FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("login-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public  void init(){
		// this is called automatically before start()

		// retrieve application state from the DB
		try {
			AppStateManager.loadAppState();
		} catch (AppStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(){
		// this is called automatically after Platform.exit()

		// save application state on the DB
		try {
			AppStateManager.saveAppState();
		} catch (AppStateException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}

}