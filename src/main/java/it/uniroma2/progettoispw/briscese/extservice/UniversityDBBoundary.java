package it.uniroma2.progettoispw.briscese.extservice;

public class UniversityDBBoundary {

	public UniDBBean isStudentEnrolled(UniDBBean bean) {
		int isEnrolled = DummyUniversityDB.getInstance().isEnrolled(bean.getEnrollNumber());

		bean.setEnrolled(isEnrolled == 1);

		return bean;
	}
}
