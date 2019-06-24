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
			String sql = "SELECT film.id, "
					+ "film.title, "
					+ "film.description, "
					+ "film.release_year, "
					+ "film.language_id, "
					+ "film.rental_duration, "
					+ "film.rental_rate, "
					+ "film.length, "
					+ "film.replacement_cost, "
					+ "film.rating, "
					+ "film.special_features, "
					+ "language.name "
					+ "FROM film "
					+ "JOIN language "
					+ "on film.language_id = language.id "
					+ "WHERE film.id = ?";
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
				films.setLanguage(rs.getString("language.name"));
				
				films.setActors(findActorsByFilmId(filmId));

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
			String sql = "SELECT id, "
					+ "first_name, "
					+ "last_name from actor "
					+ "where id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id, "
					+ "actor.first_name, "
					+ "actor.last_name " 
					+ "FROM actor "
					+ "JOIN film_actor "
					+ "ON actor.id = film_actor.actor_id "
					+ "WHERE film.id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

	public List<Film> findFilmByKeyWord(String filmKeyW) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, "
					+ "film.title, "
					+ "film.description, "
					+ "film.release_year,"
					+ "film.language_id, "
					+ "film.rental_duration, "
					+ "film.rental_rate, "
					+ "film.length, "
					+ "film.replacement_cost, "
					+ "film.rating, "
					+ "film.special_features, "
					+ "language.name "
					+ "FROM film "
					+ "JOIN language on film.language_id = language.id "
					+ "WHERE "
					+ "film.title LIKE ? "
					+ "OR "
					+ "film.description LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + filmKeyW + "%");
			pstmt.setString(2, "%" + filmKeyW + "%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_Duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setLanguage(rs.getString("language.name"));
				films.add(film);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

}
