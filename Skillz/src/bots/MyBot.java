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

		final ArrayList<Asteroid> livingAstroids = new ArrayList<Asteroid>();
        livingAstroids.addAll(Arrays.asList(game.getLivingAsteroids()));

		final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
        capsules.addAll(Arrays.asList(game.getMyCapsules()));
		
		availablePirates.clear();
        livingAstroids.clear();
        motherships.clear();
        capsules.clear();
		// Try to push, if you didn't - take the capsule and go to the mothership.
	}

	
}
