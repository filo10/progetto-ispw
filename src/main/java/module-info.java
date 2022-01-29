module it.uniroma2.progettoispw {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.sql.rowset;


	opens it.uniroma2.progettoispw.briscese.ui to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui1 to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui2 to javafx.fxml;
	exports it.uniroma2.progettoispw;
	opens it.uniroma2.progettoispw.briscese.ui.gui2.passenger to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui2.verifier to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui2.driver to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui1.passenger to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui1.verifier to javafx.fxml;
	opens it.uniroma2.progettoispw.briscese.ui.gui1.driver to javafx.fxml;
}