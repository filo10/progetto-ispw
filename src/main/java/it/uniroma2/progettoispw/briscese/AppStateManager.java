package it.uniroma2.progettoispw.briscese;

import it.uniroma2.progettoispw.briscese.dao.RideDAO;
import it.uniroma2.progettoispw.briscese.dao.UpgradeRequestDAO;
import it.uniroma2.progettoispw.briscese.dao.UserDAO;
import it.uniroma2.progettoispw.briscese.exceptions.AppStateException;
import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
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
			RideDAO.getInstance().retrieveRides();

			DBConnectionManager.getInstance().closeConnection();

		} catch (SQLException | DBConnectionException | CannotAddRoleException | UserNotFoundException | RoleException e) {
			String message = "Critical error while retrieving from DB:\n" + e.getMessage();
			// TODO chiudi la connessione
			throw new AppStateException(message);
		}
	}

	public static void saveAppState() throws AppStateException {
		try {
			DBConnectionManager.getInstance().openConnection();

			UserDAO.getInstance().saveUsersintoDB();
			UpgradeRequestDAO.getInstance().saveUpgradeRequests();
			RideDAO.getInstance().saveRides();

			DBConnectionManager.getInstance().closeConnection();
		} catch (SQLException | DBConnectionException e) {
			String message = "Critical error:\n" + e.getMessage();
			// TODO chiudi la connessione...
			// TODO vedi chiusura connessione anche per deletePasenger in ridedao
			throw new AppStateException(message);
		}
	}
}
