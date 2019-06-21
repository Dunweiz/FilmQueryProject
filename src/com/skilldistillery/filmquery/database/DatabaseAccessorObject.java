package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private final String user = "student";
	private final String pass = "student";

	public DatabaseAccessorObject() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film films = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id,"
					+ "rental_duration, rental_rate, length, replacement_cost," + "rating, special_features"
					+ " FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				films = new Film();
				films.setId(rs.getInt("id"));
				films.setTitle(rs.getString("title"));
				films.setDescription(rs.getString("description"));
				films.setReleaseYear(rs.getInt("release_year"));
				films.setLanguageId(rs.getInt("language_id"));
				films.setRentalDuration(rs.getInt("rental_duration"));
				films.setRentalRate(rs.getDouble("rental_rate"));
				films.setLength(rs.getInt("length"));
				films.setReplacementCost(rs.getDouble("replacement_cost"));
				films.setRating(rs.getString("rating"));
				films.setSpecialFeatures(rs.getString("special_features"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}
