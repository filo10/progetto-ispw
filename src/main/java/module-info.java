module it.uniroma2.progettoispw {
	requires javafx.controls;
	requires javafx.fxml;


	opens it.uniroma2.progettoispw to javafx.fxml;
	exports it.uniroma2.progettoispw;
}