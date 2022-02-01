package it.uniroma2.progettoispw.briscese.ui.gui1;

import it.uniroma2.progettoispw.briscese.ui.SessionToken;

public abstract class PageController {
	protected SessionToken sessionToken;

	public void shareSessionToken(SessionToken token) {
		this.sessionToken = token;
	}
}
