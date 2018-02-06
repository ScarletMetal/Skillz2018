package bots;

import java.util.*;
import pirates.*;

public class MyBot implements PirateBot {

    	private static final ArrayList<Pirate> livingPirates	= new ArrayList<Pirate>();
		private static final ArrayList<Mothership> motherships	= new ArrayList<Mothership>();
    	private static final ArrayList<Asteroid> livingAstroids	= new ArrayList<Asteroid>();
    	private static final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
    	private static final ArrayList<Pirate> collectors = new ArrayList<Pirate>();
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

		for (int i = 0; i < game.getMyLivingPirates().length; i++) {
			livingPirates.add(game.getMyLivingPirates()[i]);
		}

		for (int i = 0; i < game.getMyMotherships().length; i++) {
			motherships.add(game.getMyMotherships()[i]);
		}

		for (int i = 0; i < game.getLivingAsteroids().length; i++) {
			livingAstroids.add(game.getLivingAsteroids()[i]);
		}

		for (int i = 0; i < game.getMyCapsules().length; i++) {
			capsules.add(game.getMyCapsules()[i]);
		}

		
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

	private void SailToCapsule() {

	}
	
    private static void resetArrayLists(){
        livingPirates.clear();
        livingAstroids.clear();
        motherships.clear();
        capsules.clear();
        collectors.clear();
    }

}
