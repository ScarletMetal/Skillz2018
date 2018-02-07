package bots;

import java.util.*;
import pirates.*;

public class MyBot implements PirateBot {

	
	private static final ArrayList<Pirate> availablePirates = new ArrayList<Pirate>();

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

        availablePirates.addAll(Arrays.asList(game.getMyLivingPirates()));

		final ArrayList<Mothership> motherships = new ArrayList<Mothership>();
		motherships.addAll(Arrays.asList(game.getMyMotherships()));

		final ArrayList<Asteroid> livingAsteroids = new ArrayList<Asteroid>();
		livingAsteroids.addAll(Arrays.asList(game.getLivingAsteroids()));

		final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
		capsules.addAll(Arrays.asList(game.getMyCapsules()));
		
		avoidBoulders(livingAsteroids, game);
		
		sailToCapsule();

		availablePirates.clear();
		livingAsteroids.clear();
		motherships.clear();
		capsules.clear();
		// Try to push, if you didn't - take the capsule and go to the mothership.
	}

	private static void avoidBoulders(ArrayList<Asteroid> asteroids, PirateGame game) {
		for (Pirate pirate : availablePirates) {
			for (Asteroid asteroid : asteroids) {
				if (pirate.canPush(asteroid))
					pirate.push(asteroid, (game.getEnemyCapsules()[0] != null) ? game.getEnemyCapsules()[0].location
							: /* this option will be replaced with nearest enemy when available*/game.getEnemyLivingPirates()[0]);
				else if(pirate.getLocation().equals(asteroid.getLocation().add(asteroid.direction)))
					pirate.sail(pirate.initialLocation);
			}
		}
	}
}
