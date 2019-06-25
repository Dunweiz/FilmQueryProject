package com.skilldistillery.filmquery.app;

import java.util.*;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	boolean run = false;

	public static void main(String[] args) throws ClassNotFoundException {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

//	private void test() {
//		Film film = db.findFilmById(1);
//		List<Actor> actors = db.findActorsByFilmId(1);
//		System.out.println(film);
//		System.out.println(actors.size());
//		film.printActors();
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		do {
			try {
				startUserInterface(input);
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println();
				startUserInterface(input);
				input.nextLine();
			}

		} while (!run);
		input.close();
	}

	private void startUserInterface(Scanner input) {

		System.out.println("+====================================+");
		System.out.println("Welcome to Detlux Film's we can do the following for you:");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
		System.out.println("+====================================+");
		int userInput = 0;
		try {
			userInput = input.nextInt();
		} catch (Exception e1) {
			System.out.println("invalid input");

		}
		switch (userInput) {
		case 1:
			System.out.println("Please enter an ID number");
			int idNumber;
			idNumber = input.nextInt();
			Film filmById = db.findFilmById(idNumber);
			if (filmById instanceof Film) {
				System.out.println(filmById);
				List<Actor> actor = db.findActorsByFilmId(filmById.getId());
				System.out.print("Actors: \t");
				System.out.println(actor);
			} else {
				System.out.println("Film not found");
				idNumber = input.nextInt();
			}
			break;
		case 2:
			System.out.println("Please enter a Key word");
			String userKeyWord = input.next();
			List<Film> films = db.findFilmByKeyWord(userKeyWord);
			if (films instanceof Film) {
				for (Film movie : films) {
					System.out.println(movie);
					List<Actor> actors = db.findActorsByFilmId(movie.getId());
					System.out.println(actors);
				}
			} else {
				System.out.println("There is no film with the word" + " " + userKeyWord);
			}

			break;
		case 3:
			System.out.println("Good bye");
			run = true;

		}

	}

}
