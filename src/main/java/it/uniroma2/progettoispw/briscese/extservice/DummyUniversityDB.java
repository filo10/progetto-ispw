package it.uniroma2.progettoispw.briscese.extservice;

public class DummyUniversityDB {
	private static DummyUniversityDB instance = null;


	private DummyUniversityDB () {}

	public static synchronized DummyUniversityDB getInstance() {
		if (DummyUniversityDB.instance == null)
			DummyUniversityDB.instance = new DummyUniversityDB();
		return instance;
	}

	public int isEnrolled(int enrollNumber) {
		/* return -1 if false, 1 if true */
		if (enrollNumber > 300000 || enrollNumber < 100000)
			return -1;
		int count = 0;
		for(; enrollNumber != 0; enrollNumber/=10, ++count);
		if (count == 6) // if enrollNumber has 6 digits
			return 1;
		else return -1;
	}
}
