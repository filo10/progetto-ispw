import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.model.License;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.roles.Driver;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

	/*
	 *	Author: Filippo Maria Briscese
	 */

	@Test
	public void TestAddRole_DriverToNonPassenger() {
		boolean output = false;
		try {
			User user = new User(1000, "name surname", "pwd");

			user.addRole(new Verifier()); // if role added is not passenger this test does pass

			user.addRole(new Driver(new License("licenseCod3", LocalDate.of(2099, 1, 1))));

		} catch (CannotAddRoleException e) {
			output = true;
		}
		finally {
			assertEquals(true, output);
		}

	}
}
