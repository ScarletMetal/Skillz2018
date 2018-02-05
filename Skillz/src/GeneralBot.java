	import java.util.ArrayList;
import java.util.*;
	import pirates.*;
	
public class GeneralBot implements PirateBot{


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
	    	
	    	ArrayList <Pirate> livingPirates= new ArrayList<Pirate>();
	    	for (int i = 0; i < game.getMyLivingPirates().length; i++) {
		        livingPirates.add(game.getMyLivingPirates()[i]); 
			}
	    	ArrayList <Mothership> motherships = new ArrayList<Mothership>();
	    	for (int i = 0; i < game.getMyMotherships().length; i++) {
		        motherships.add(game.getMyMotherships()[i]); 
			}
	    	ArrayList <Asteroid> astroids= new ArrayList<Asteroid>();
	    	for (int i = 0; i < game.getLivingAsteroids().length; i++) {
		        astroids.add(game.getLivingAsteroids()[i]); 
			}
	        
	        
	        for (int i = 0; i < livingPirates.size(); i++) {
		        if (!tryPush(livingPirates.get(i), game)) {
		            // If the pirate doesn't have a capsule, go and get it!
		            if (livingPirates.get(i).capsule == null) {
		                Capsule capsule =game.getMyCapsules()[0];
		                livingPirates.get(i).sail(capsule);
		            }
		            // Else, go to my mothership.
		            else {
		                // Get my mothership.
		                Mothership mothership = game.getMyMotherships()[0];
		                // Go towards the mothership.
		                livingPirates.get(i).sail(mothership);
		            }
		        }
			}
	        // Try to push, if you didn't - take the capsule and go to the mothership.
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

