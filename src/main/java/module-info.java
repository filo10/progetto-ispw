module it.uniroma2.progettoispw {
	requires javafx.controls;
	requires javafx.fxml;


	opens it.uniroma2.progettoispw.briscese.gui1 to javafx.fxml;
	exports it.uniroma2.progettoispw;
}