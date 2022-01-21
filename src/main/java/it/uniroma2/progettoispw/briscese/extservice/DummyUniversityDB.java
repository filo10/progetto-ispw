package it.uniroma2.progettoispw.briscese.extservice;

public class DummyUniversityDB {
	private static DummyUniversityDB instance = null;


	private DummyUniversityDB () {}

	public static synchronized DummyUniversityDB getInstance() {
		if (DummyUniversityDB.instance == null)
			DummyUniversityDB.instance = new DummyUniversityDB();
		return instance;
	}

	public boolean isEnrolled(int enrollNumber) {
		if (enrollNumber < 300000)
			return false;
		int count = 0;
		for(; enrollNumber != 0; enrollNumber/=10, ++count);
		return (count == 6);
	}
}
