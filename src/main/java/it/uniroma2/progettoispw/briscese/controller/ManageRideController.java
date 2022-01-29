package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.exceptions.ManageRideException;
import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.model.RideCatalog;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;

import java.time.LocalDate;
import java.time.LocalTime;

public class ManageRideController {

	public void offerRide(RideBean bean) throws ManageRideException {
		try {
			User driver = UserCatalog.getInstance().findUser(bean.getDriverId());

			int numberOfSeats = bean.getNumberOfSeats();
			String startPoint = bean.getStartPoint();
			String finishPoint = bean.getFinishPoint();
			LocalDate date = LocalDate.parse(bean.getDate());
			LocalTime time = LocalTime.parse(bean.getTime());

			RideCatalog.getInstance().newRide(driver, numberOfSeats, startPoint, finishPoint, date, time);

		} catch (UserNotFoundException | RoleException e) {
			throw new ManageRideException(e.getMessage());
		}
	}

	/* TODO
	elimina corsa :
		// chiedi al catalogo? elimina corsa
		// notifica passeggeri della corsa
		// notifica richieste di passaggio
	--
	 */

}
