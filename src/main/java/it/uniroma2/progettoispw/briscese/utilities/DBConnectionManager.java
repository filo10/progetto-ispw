package it.uniroma2.progettoispw.briscese.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBConnectionManager {
	private static DBConnectionManager instance = null;

	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private Connection connection;

	private DBConnectionManager() {}

	public static synchronized DBConnectionManager getInstance() throws DBConnectionException {
		try {
			if (instance == null) {
				instance = new DBConnectionManager();
				instance.retrieveConfiguration();
			}
			return instance;
		} catch (IOException e) {
			throw new DBConnectionException(e.getMessage(), e.getCause());
		}
	}

	private void retrieveConfiguration() throws IOException {
		try (BufferedReader inStream = new BufferedReader(new FileReader("db.txt"));)
		{
			dbUrl = inStream.readLine();
			dbUser = inStream.readLine();
			dbPass= inStream.readLine();
		}
	}

	public void openConnection() throws SQLException {
		connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
	}

	public Statement getStatement() throws SQLException {
		return connection.createStatement();
	}

	public void closeConnection() throws SQLException {
		if (connection != null)
			connection.close();
	}

	public Statement getScrollableStatement() throws SQLException {
		return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
}
