package it.uniroma2.progettoispw;

import java.sql.*;

public class ProvaDB {

	public static void prova() {

		try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/progetto-ispw", "root", "password");
			 Statement statement = connection.createStatement();)
		{
			ResultSet resultSet = statement.executeQuery("select * from users");

			while (resultSet.next()) {
				System.out.println(resultSet.getString("name") + " " + resultSet.getString("surname"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("***fine***");
		}

	}
}
