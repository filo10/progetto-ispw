package it.uniroma2.progettoispw.briscese.extservice;

public class UniDBBean {
	private int enrollNumber;
	private boolean isEnrolled;


	public UniDBBean(int enrollNumber) {
		this.enrollNumber = enrollNumber;
	}

	public int getEnrollNumber() {
		return enrollNumber;
	}

	public boolean isEnrolled() {
		return isEnrolled;
	}

	public void setEnrolled(boolean enrolled) {
		isEnrolled = enrolled;
	}
}
