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
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReserveSeatController {

	public List<RideBean> getAvailableRides() {
		List<RideBean> list = new ArrayList<>();
		List<Ride> listToSort = RideCatalog.getInstance().getAvailableRides();
		listToSort.sort(Comparator.comparing(Ride::getDate)); // sort by date

		for (Ride ride : listToSort)
			list.add(new RideBean(ride, ride.getDriver().getFullName()));

		return list;
	}

	public void sendRequest(SeatRequestBean bean) throws SeatRequestException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			User user = UserCatalog.getInstance().findUser(bean.getPassengerId());
			ride.requestSeat(user);
		} catch (RideNotFoundException | UserNotFoundException e) {
			throw new SeatRequestException(e.getMessage());
		}
	}

	public void cancelRequest(SeatRequestBean bean) throws SeatRequestException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			User user = UserCatalog.getInstance().findUser(bean.getPassengerId());
			ride.cancelSeatRequest(user);
		} catch (UserNotFoundException | SeatRequestException | RideNotFoundException e) {
			throw new SeatRequestException(e.getMessage());
		}
	}

	public void leaveRide(SeatRequestBean bean) throws SeatRequestException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			User user = UserCatalog.getInstance().findUser(bean.getPassengerId());
			ride.leaveRide(user);
		} catch (UserNotFoundException | SeatRequestException | RideNotFoundException | SQLException | DBConnectionException e) {
			throw new SeatRequestException(e.getMessage());
		}
	}

	public List<RideBean> getMyRequests(SeatRequestBean bean) throws UserNotFoundException {
		User user = UserCatalog.getInstance().findUser(bean.getPassengerId());
		List<Ride> rideList = RideCatalog.getInstance().getRides();
		List<RideBean> list = new ArrayList<>();

		for (Ride ride : rideList) {
			if (ride.getRequestList().contains(user)) {
				list.add(new RideBean(ride, ride.getDriver().getFullName()));
			}
		}

		return list;
	}

	public List<RideBean> getMyReservedSeats(SeatRequestBean bean) throws UserNotFoundException {
		User user = UserCatalog.getInstance().findUser(bean.getPassengerId());
		List<Ride> rideList = RideCatalog.getInstance().getRides();
		List<RideBean> list = new ArrayList<>();

		for (Ride ride : rideList) {
			if (ride.getPassengerList().contains(user)) {
				list.add(new RideBean(ride, ride.getDriver().getFullName()));
			}
		}

		return list;
	}
}
