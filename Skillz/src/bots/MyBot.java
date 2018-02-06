package bots;

import java.util.*;
import pirates.*;

public class MyBot implements PirateBot {

	/**
	 * This is an example for a bot.
	 */
	/**
	 * Makes the bot run a single turn
	 *
	 * @param game
	 *            - the current game state.
	 */
	public void doTurn(PirateGame game) {
		// Get one of my pirates.

		ArrayList<Pirate> livingPirates = new ArrayList<Pirate>();
		for (int i = 0; i < game.getMyLivingPirates().length; i++) {
			livingPirates.add(game.getMyLivingPirates()[i]);
		}

		ArrayList<Mothership> motherships = new ArrayList<Mothership>();
		for (int i = 0; i < game.getMyMotherships().length; i++) {
			motherships.add(game.getMyMotherships()[i]);
		}

		ArrayList<Asteroid> livingAstroids = new ArrayList<Asteroid>();
		for (int i = 0; i < game.getLivingAsteroids().length; i++) {
			livingAstroids.add(game.getLivingAsteroids()[i]);
		}

		ArrayList<Capsule> capsules = new ArrayList<Capsule>();
		for (int i = 0; i < game.getMyCapsules().length; i++) {
			capsules.add(game.getMyCapsules()[i]);
		}

		for (int i = 0; i < livingPirates.size(); i++) {
			if (!tryPush(livingPirates.get(i), game)) {
				// If the pirate doesn't have a capsule, go and get it!
				if (livingPirates.get(i).capsule == null) {
					SailToCapsule(capsules, livingPirates);
				}
				// Else, go to my mothership.
				else {

					livingPirates.get(i).sail(motherships.get(0));
				}
			}
		}
		// Try to push, if you didn't - take the capsule and go to the mothership.
	}

	/**
	 * Makes the pirate try to push an enemy pirate or an asteroid. Returns True if
	 * it did.
	 *
	 * @param pirate
	 *            - the pushing pirate
	 * @param game
	 *            - the current game state
	 * @return - true if the pirate pushed
	 */
	private boolean tryPush(Pirate pirate, PirateGame game) {
		// Go over all enemies
		for (Pirate enemy : game.getEnemyLivingPirates()) {
			// Check if the pirate can push the enemy
			if (pirate.canPush(enemy)) {
				// Push enemy!
				pirate.push(enemy, enemy.initialLocation);

				// Print a message
				System.out.println("pirate " + pirate + " pushes " + enemy + " towards " + enemy.initialLocation);

				// Did push
				return true;
			}
		}

		// Go over all living asteroids
		for (Asteroid asteroid : game.getLivingAsteroids()) {
			// Check if the pirate can push the asteroid
			if (pirate.canPush(asteroid)) {
				// Push asteroid!
				pirate.push(asteroid, game.getEnemyCapsules()[0]);

				// Print a message
				System.out
						.println("pirate " + pirate + " pushes " + asteroid + " towards " + game.getEnemyCapsules()[0]);

				// Did push
				return true;
			}
		}

		// Didn't push
		return false;
	}

	public void SailToCapsule(ArrayList<Capsule> capsule, ArrayList<Pirate> pirates) {
		ArrayList<Pirate> collectors = new ArrayList<Pirate>();

		for (int j = 0; j < pirates.size(); j++) {
			if (j + 1 >= pirates.size()) {
				for (int i = 0; i < capsule.size(); i++) {
					if (pirates.get(j).distance(capsule.get(i)) < pirates.get(j + 1).distance(capsule.get(i))) {
						collectors.add(pirates.get(j));
						if (pirates.size() != 0)
							pirates.remove(j);
						collectors.get(collectors.size() - 1).sail(capsule.get(i));
					}
				}
			}
		}
	}

	public static void Campers() {

		// Enemy Capsules
		ArrayList<Capsule> enemyCapsules = new ArrayList<Capsule>();
		for (int i = 0; i < game.getEnemyCapsules().length; i++) {
			enemyCapsules.add((Capsule) game.getEnemyCapsules()[i]);
		}

		// Enemy Capsule Holders

		ArrayList<Pirate> enemyCapsuleHolders = new ArrayList<Pirate>();

		for (Capsule capsule : enemyCapsules) {

			if (capsule.holder != null)
				enemyCapsuleHolders.add((Pirate) capsule.holder);

		}

		// Set Camper Pirates
		ArrayList<Pirate> myCamperPirates = new ArrayList<Pirate>();
		for (int i = 0; i < 2; i++) {
			myCamperPirates.add((Pirate) availablePirates.get(i));

		}

		// Find enemy Mothership
		ArrayList<Mothership> enemyMotherships = new ArrayList<Mothership>();
		for (int i = 0; i < game.getEnemyMotherships().length; i++) {
			enemyMotherships.add(game.getEnemyMotherships()[i]);
		}

		// TO DO:Initialize Campers

		if (enemyMotherships.size() == 1) {

			for (Mothership mothership : enemyMotherships) {

				for (Pirate enemyCapsulePirate : enemyCapsuleHolders) {

					for (Pirate camper : myCamperPirates) {

						if (enemyCapsulePirate.distance(mothership) <= 800 && !camper.canPush(enemyCapsulePirate)) {
							camper.sail(enemyCapsulePirate.getLocation());
						} else if (camper.canPush(enemyCapsulePirate)) {
							// ATM its to mothership num 0
							camper.push(enemyCapsulePirate, game.getMyMotherships()[0]);
						}

						if (camper.distance(mothership) <= 250) {
							camper.sail(mothership.getLocation());
						}

					}

				}

			}

		}
	}

	
}
