package com.skilldistillery.filmquery.app;

import java.util.*;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	boolean run = true;

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
		while (run)
			try {
				startUserInterface(input);
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println();
				input.nextLine();
			}

		input.close();
	}

	private void startUserInterface(Scanner input) throws Exception {
		System.out.println("+====================================+");
		System.out.println("Welcome to Detlux Film's we can do the following for you:");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
		System.out.println("+====================================+");

		int userInput;
		try {
			userInput = input.nextInt();
			switch (userInput) {
			case 1:
				System.out.println("Please enter an ID number");
				int idNumber;
				try {
					idNumber = input.nextInt();
					Film filmById = db.findFilmById(idNumber);
					System.out.println(filmById);
				} catch (Exception e) {
					System.out.println("Film not found");
				}
				break;
			case 2:
				System.out.println("Please enter a Key word");
				String userKeyWord;
				try {
					userKeyWord = input.next();
					for (Film movie : db.findFilmByKeyWord(userKeyWord)) {
						if (movie instanceof Film) {
							System.out.println(movie);
							List<Actor> actors = db.findActorsByFilmId(movie.getId());
							System.out.println(actors);
						} else {
							System.out.println("There is no film with the word" + userKeyWord);
						}
					}
				} catch (Exception e) {
					System.out.println("That's an invalid input");
				}

				break;
			case 3:
				run = false;
			}
		} catch (Exception e) {
			input.hasNextInt();
			System.out.println("Thats not a valid input");
		}

	}

}
