package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.exceptions.RideNotFoundException;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.exceptions.UserNotFoundException;
import it.uniroma2.progettoispw.briscese.model.Ride;
import it.uniroma2.progettoispw.briscese.model.RideCatalog;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReserveSeatContorller {

	public List<RideBean> getAvailableRides() {
		List<RideBean> list = new ArrayList<>();
		List<Ride> listToSort = new ArrayList<>();

		listToSort = RideCatalog.getInstance().getAvailableRides();
		listToSort.sort(Comparator.comparing(Ride::getDate)); // sort by date

		for (Ride ride : listToSort)
			list.add(new RideBean(ride, ride.getDriver().getFullName()));

		return list;
	}

	public void sendRequest(SeatRequestBean bean) throws SeatRequestException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			User user = null;
			user = UserCatalog.getInstance().findUser(bean.getPassengerId());
			ride.requestSeat(user);
		} catch (RideNotFoundException | UserNotFoundException e) {
			throw new SeatRequestException(e.getMessage());
		}
	}

	// TODO cancel request
	// leave Ride
}
