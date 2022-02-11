import it.uniroma2.progettoispw.briscese.exceptions.CannotAddRoleException;
import it.uniroma2.progettoispw.briscese.model.UpgradeRequest;
import it.uniroma2.progettoispw.briscese.model.UpgradeRequestStatus;
import it.uniroma2.progettoispw.briscese.model.User;
import it.uniroma2.progettoispw.briscese.model.roles.Verifier;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUpgradeRequest {

	@Test
	public void testClose() {
		UpgradeRequest request = new UpgradeRequest();

		User verifier = new User(10011,"name verifier", "pas");
		try {
			verifier.addRole(new Verifier());
		} catch (CannotAddRoleException e) {
			e.printStackTrace();
		}

		request.close(true, verifier); // commenting or using false as argument make the test fail.

		assertEquals(UpgradeRequestStatus.APPROVED, request.getStatus());

	}
}
