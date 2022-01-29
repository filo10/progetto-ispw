package it.uniroma2.progettoispw.briscese.exceptions;

public class UpgradeRequestNotFoundException extends Exception {

	public UpgradeRequestNotFoundException(int reqId) {
		super("No Upgrade Request found with requestId="+reqId);
	}
}
