package it.uniroma2.progettoispw.briscese;

import it.uniroma2.progettoispw.briscese.dao.UserDAO;
import it.uniroma2.progettoispw.briscese.exceptions.AppStateException;
import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionManager;

import java.sql.SQLException;


public class AppStateManager {
	private AppStateManager() {}

	public static void loadAppState() throws AppStateException {
		try {
			DBConnectionManager.getInstance().openConnection();

			UserDAO.getInstance().retrieveUsersFromDB();
			// TODO richieste upgrade
			// corse e loro passegeri
			// richieste passaggio
			// recensioni


			DBConnectionManager.getInstance().closeConnection();

		} catch (SQLException | DBConnectionException | CannotAddRoleException e) {
			String message = "Critical error:\n" + e.getMessage();
			throw new AppStateException(message);
		}
	}

	public static void saveAppState() throws AppStateException {
		try {
			DBConnectionManager.getInstance().openConnection();

			UserDAO.getInstance().saveUsersintoDB();
			// TODO richieste upgrade
			// corse e loro passeggeri
			// richieste passaggio
			// recensioni

			DBConnectionManager.getInstance().closeConnection();
		} catch (SQLException | DBConnectionException e) {
			String message = "Critical error:\n" + e.getMessage();
			throw new AppStateException(message);
		}
	}
}
