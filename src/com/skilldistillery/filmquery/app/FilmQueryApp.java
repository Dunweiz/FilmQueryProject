package com.skilldistillery.filmquery.app;
import java.util.*;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {


	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws ClassNotFoundException {
		FilmQueryApp app = new FilmQueryApp();
		//app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		List <Actor> actors = db.findActorsByFilmId(1);
		System.out.println(film);
		System.out.println(actors.size());
		film.printActors();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean stayOn = true;
		do {
			System.out.println("+====================================+");
			System.out.println("Welcome to Detlux Film's we can do the following for you:");
			System.out.println("1. Look up a film by its id.");
			System.out.println("2. Look up a film by a search keyword.");
			System.out.println("3. Exit the application.");
			System.out.println("+====================================+");
			int userInput = input.nextInt();
			switch(userInput){
			case 1 :System.out.println("Please enter an ID number");
					int idNumber = input.nextInt();
					Film filmById = db.findFilmById(idNumber);
					if(filmById instanceof Film) {
						System.out.println(filmById);
					}else {
						System.out.println("Film not found");
					}
					break;
			case 2 :System.out.println("Please enter a Key word");
			 		String userKeyWord = input.next();
			 		for (Film films : db.findFilmByKeyWord(userKeyWord)) {
			 			if(films instanceof Film) {
			 				System.out.println(films);
			 			}else {
			 				System.out.println("there is nothing");			 				
			 			}
						
					}
			 		break;		
			case 3 :
				stayOn = false;
			}
				
			
		}while(stayOn);
	}

}
