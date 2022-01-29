package it.uniroma2.progettoispw.briscese;

import it.uniroma2.progettoispw.briscese.dao.UpgradeRequestDAO;
import it.uniroma2.progettoispw.briscese.dao.UserDAO;
import it.uniroma2.progettoispw.briscese.exceptions.AppStateException;
import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionManager;

import java.sql.SQLException;


public class AppStateManager {
	private AppStateManager() {}

	public static void loadAppState() throws AppStateException {
		try {
			DBConnectionManager.getInstance().openConnection();

			UserDAO.getInstance().retrieveUsersFromDB();
			UpgradeRequestDAO.getInstance().retrieveUpgradeRequests();

			// TODO corse e loro passegeri
			// richieste passaggio
			// recensioni


			DBConnectionManager.getInstance().closeConnection();

		} catch (SQLException | DBConnectionException | CannotAddRoleException | UserNotFoundException e) {
			String message = "Critical error:\n" + e.getMessage();
			throw new AppStateException(message);
		}
	}

	public static void saveAppState() throws AppStateException {
		try {
			DBConnectionManager.getInstance().openConnection();

			UserDAO.getInstance().saveUsersintoDB();
			UpgradeRequestDAO.getInstance().saveUpgradeRequests();

			// TODO corse e loro passeggeri
			// richieste passaggio
			// recensioni

			DBConnectionManager.getInstance().closeConnection();
		} catch (SQLException | DBConnectionException e) {
			String message = "Critical error:\n" + e.getMessage();
			throw new AppStateException(message);
		}
	}
}
