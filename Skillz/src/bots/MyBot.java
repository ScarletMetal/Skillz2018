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

		final ArrayList<Pirate> livingPirates = new ArrayList<Pirate>();
        livingPirates.addAll(Arrays.asList(game.getMyLivingPirates()));

		final ArrayList<Mothership> motherships = new ArrayList<Mothership>();
        motherships.addAll(Arrays.asList(game.getMyMotherships()));

		final ArrayList<Asteroid> livingAstroids = new ArrayList<Asteroid>();
        livingAstroids.addAll(Arrays.asList(game.getLivingAsteroids()));

		final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
        capsules.addAll(Arrays.asList(game.getMyCapsules()));

		for (Pirate pirate : livingPirates) {
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

	// Num of pirates pushing EnemyCapsuleHolder at the same time
		private boolean canPushEnemyCapsule (ArrayList<Pirate> pushingPirates, Pirate enemyCapsulePirate, PirateGame game) {
			
			int counter = 0;
			
			//check if Capsule Pirate can be pushed
			if (enemyCapsulePirate != null) {
		
			for (Pirate pirate : pushingPirates) {
				if(pirate.canPush(enemyCapsulePirate))
					counter++;	
			}
			
		}
			
			if( counter == pushingPirates.size()) 
				return true;
			else
				return false;
	}

	
	private void Campers(ArrayList<Pirate> avialablePirates, PirateGame game, Mothership enemyMothership) {

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
			myCamperPirates.add((Pirate) avialablePirates.get(i));
			avialablePirates.remove(i);

		}


		for (Pirate enemyCapsulePirate : enemyCapsuleHolders) {

			for (Pirate camper : myCamperPirates) {

				//Check if enemy CapsulePirate is within distance and push if necessary 
				if (enemyCapsulePirate.distance(enemyMothership) <= 800 && !canPushEnemyCapsule(myCamperPirates, enemyCapsulePirate, game)) {
							camper.sail(enemyCapsulePirate.getLocation());
				} else if (canPushEnemyCapsule(myCamperPirates, enemyCapsulePirate, game)) {
							camper.push(enemyCapsulePirate, game.getMyMotherships()[0]);
						}

				if (camper.distance(enemyMothership) <= 250) {
							camper.sail(enemyMothership.getLocation());
						}

					}

				}

			}
	
	}



	
