package junit;

import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.exceptions.RoleException;
import it.uniroma2.progettoispw.briscese.exceptions.SeatRequestException;
import it.uniroma2.progettoispw.briscese.model.License;
import it.uniroma2.progettoispw.briscese.model.Ride;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;
import it.uniroma2.progettoispw.briscese.model.roles.Passenger;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRide {

	/*
	*	Author: Filippo Maria Briscese
	*/

	@Test
	public void testSeatsAvailable() {
		try {
			User driver = new User(1000, "Tizio Caio", "password");
			driver.addRole(new Passenger());
			driver.addRole(new Driver(new License("licensecode", LocalDate.parse("2100-01-01"))));
			Ride ride = new Ride(0, driver, 5, "start", "finish", LocalDate.of(2030, 1, 1), LocalTime.of(10, 0));

			int seatsAvailable = ride.seatsAvailable();

			assertEquals(5, seatsAvailable, 0);
		} catch (RoleException | CannotAddRoleException e) {
			e.printStackTrace(); // this should never be executed
		}
	}

	@Test
	public void testRequestSeat_FullRide() {
		boolean output = false;

		try {
			User driver = new User(1000, "John Doe", "p");
			driver.addRole(new Passenger());
			driver.addRole(new Driver(new License("licenseC0ode", LocalDate.parse("2090-01-01"))));
			Ride ride = new Ride(0, driver, 2, "st", "fin", LocalDate.of(2050, 1, 1), LocalTime.of(10, 0));

			User passenger1 = new User(1001, "One", "one", new Passenger());
			User passenger2 = new User(1002, "two", "two", new Passenger());
			User passenger3 = new User(1003, "three", "three", new Passenger());

			ride.requestSeat(passenger1);
			ride.replyRequest(passenger1, true);

			ride.requestSeat(passenger2);
			ride.replyRequest(passenger2, true);

			// commenting those 2 lines should make test fail
			ride.requestSeat(passenger3); // this passenger shouldn't be accepted and requestSeat should throw a SeatRequestException with a specific message.
			ride.replyRequest(passenger3, true); // so this line should never be executed.

		}
		catch (RoleException | CannotAddRoleException e) {
			e.printStackTrace(); // this shouldn't be executed... if so there is a bug in this test
		} catch (SeatRequestException expected) {
			if (expected.getMessage().equals("Cannot send request: the ride is full."))
				output = true;
		}
		finally {
			assertEquals(true, output);
		}
	}

	@Test
	public void testRequestSeat_RideInThePast() {
		boolean output = false;

		try {
			User driver = new User(1000, "Pinco Pallino", "pw");
			driver.addRole(new Passenger());
			driver.addRole(new Driver(new License("codePinc0", LocalDate.parse("2084-01-01"))));

			// setting the date after today should make the test fail
			Ride ride = new Ride(0, driver, 2, "inizio", "fine", LocalDate.of(1950, 1, 1), LocalTime.of(10, 0));

			User passenger1 = new User(1001, "One", "one", new Passenger());
			ride.requestSeat(passenger1); // this line should throw the expected exception
			ride.replyRequest(passenger1, true);

		}
		catch (RoleException | CannotAddRoleException e) {
			e.printStackTrace(); // this shouldn't be executed... if so there is a bug in this test
		} catch (SeatRequestException expected) {
			if (expected.getMessage().equals("You can't send a request for a ride in the past."))
				output = true;
		}
		finally {
			assertEquals(true, output);
		}
	}
}
