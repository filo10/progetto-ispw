package it.uniroma2.progettoispw.briscese.controller;

import it.uniroma2.progettoispw.briscese.bean.RideBean;
import it.uniroma2.progettoispw.briscese.bean.SeatReplyBean;
import it.uniroma2.progettoispw.briscese.bean.SeatRequestBean;
import it.uniroma2.progettoispw.briscese.exceptions.*;
import it.uniroma2.progettoispw.briscese.model.Ride;
import it.uniroma2.progettoispw.briscese.model.RideCatalog;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.UserCatalog;
import it.uniroma2.progettoispw.briscese.utilities.DBConnectionException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

	public List<RideBean> getMyRides(RideBean bean) {
		int driverId = bean.getDriverId();

		List<RideBean> driverRides = new ArrayList<>();
		List<Ride> myRides = new ArrayList<>();

		for (Ride ride : RideCatalog.getInstance().getRides()) {
			if (ride.getDriver().getUserId() == driverId) {
				myRides.add(ride);
			}
		}

		// sort rides by date
		myRides.sort(Comparator.comparing(Ride::getDate));
		for (Ride ride : myRides)
			driverRides.add(new RideBean(ride, null));

		return driverRides;
	}

	public RideBean getRide(RideBean bean) throws RideNotFoundException {
		Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
		return new RideBean(ride, null);
	}

	public List<SeatRequestBean> getRideSeatRequests(RideBean bean) throws RideNotFoundException {
		int rideId = bean.getRideId();
		Ride ride = RideCatalog.getInstance().findRide(rideId);
		List<SeatRequestBean> list = new ArrayList<>();

		for (User requestant : ride.getRequestList())
			list.add(new SeatRequestBean(rideId, requestant.getUserId(), requestant.getFullName()));

		return list;
	}

	public List<SeatRequestBean> getRidePassengers(RideBean bean) throws RideNotFoundException {
		int rideId = bean.getRideId();
		Ride ride = RideCatalog.getInstance().findRide(rideId);
		List<SeatRequestBean> list = new ArrayList<>();

		for (User passenger : ride.getPassengerList())
			list.add(new SeatRequestBean(rideId, passenger.getUserId(), passenger.getFullName()));

		return list;
	}

	public void replySeatRequest(SeatReplyBean bean) throws RideManagementException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			ride.replyRequest(UserCatalog.getInstance().findUser(bean.getPassengerId()), bean.getReply());
		} catch (RideNotFoundException | UserNotFoundException | SeatRequestException e) {
			throw new RideManagementException(e.getMessage());
		}
	}

	public void removePassenger(SeatReplyBean bean) throws RideManagementException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			ride.removePassenger(UserCatalog.getInstance().findUser(bean.getPassengerId()));
		} catch (RideNotFoundException | UserNotFoundException | SeatRequestException | SQLException | DBConnectionException e) {
			throw new RideManagementException(e.getMessage());
		}
	}

	public void cancelRide(RideBean bean) throws RideManagementException {
		try {
			Ride ride = RideCatalog.getInstance().findRide(bean.getRideId());
			if (LocalDate.now().compareTo(ride.getDate()) > 0) // if ride is in the past
				throw new RideManagementException("Cannot delete a ride in the past.");
			ride.deleteRide();
		} catch (RideNotFoundException e) {
			throw new RideManagementException(e.getMessage());
		}
	}

}
