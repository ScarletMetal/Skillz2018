	import java.util.Arrays;
	import java.util.List;
	import java.util.LinkedList;
	import pirates.*;
	
public class GeneralBot {


	/**
	 * This is an example for a bot.
	 */
	    /**
	     * Makes the bot run a single turn
	     *
	     * @param game - the current game state.
	     */
	    public void doTurn(PirateGame game) {
	        // Get one of my pirates.
	        Pirate pirate = game.getMyLivingPirates()[0];
	        
	        
	        // Try to push, if you didn't - take the capsule and go to the mothership.
	        if (!tryPush(pirate, game)) {
	            // If the pirate doesn't have a capsule, go and get it!
	            if (pirate.capsule == null) {
	                Capsule capsule =game.getMyCapsules()[0];
	                pirate.sail(capsule);
	            }
	            // Else, go to my mothership.
	            else {
	                // Get my mothership.
	                Mothership mothership = game.getMyMotherships()[0];
	                // Go towards the mothership.
	                pirate.sail(mothership);
	            }
	        }
	    }

	    /**
	     * Makes the pirate try to push an enemy pirate or an asteroid. Returns True if it did.
	     *
	     * @param pirate - the pushing pirate
	     * @param game - the current game state
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
	                System.out.println("pirate " + pirate + " pushes " + asteroid + " towards " + game.getEnemyCapsules()[0]);

	                // Did push
	                return true;
	            }
	        }

	        // Didn't push
	        return false;
	    }
	}

