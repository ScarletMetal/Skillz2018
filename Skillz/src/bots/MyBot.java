package bots;

import java.util.*;
import pirates.*;

public class MyBot implements PirateBot {

	
	private static final ArrayList<Pirate> availablePirates = new ArrayList<Pirate>();
	
	private static final ArrayList<Pirate> enemyPirates = new ArrayList<Pirate>();

	private static final ArrayList<Pirate> collectors = new ArrayList<Pirate>();
	
	private static final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
	
    private static final ArrayList<Asteroid> livingAsteroids = new ArrayList<Asteroid>();

	private static final ArrayList<Mothership> motherships = new ArrayList<Mothership>();


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
	    if(game.getMyLivingPirates().length>0){
		// Get one of my pirates.

        availablePirates.addAll(Arrays.asList(game.getMyLivingPirates()));

        enemyPirates.addAll(Arrays.asList(game.getEnemyLivingPirates()));

		motherships.addAll(Arrays.asList(game.getMyMotherships()));

		livingAsteroids.addAll(Arrays.asList(game.getLivingAsteroids()));

		capsules.addAll(Arrays.asList(game.getMyCapsules()));
		
		avoidBoulders(livingAsteroids, game);
		
		sailToCapsule();
	    
		for(int i=0;i<capsules.size();i++){
		    if(!canPushBoulder(collectors.get(i))){
		        if(collectors.get(i).capsule==null)
		            collectors.get(i).sail(capsules.get(i));
                else
	                collectors.get(i).sail(getNearestMothership(collectors.get(i),motherships));
		    }
		  }

		availablePirates.clear();
		livingAsteroids.clear();
		motherships.clear();
		capsules.clear();
		collectors.clear();
		// Try to push, if you didn't - take the capsule and go to the mothership.
	    }
	    
	    Campers(availablePirates, game, game.getEnemyMotherships()[0]);
	}

	private static void avoidBoulders(ArrayList<Asteroid> asteroids, PirateGame game) {
		for (Pirate pirate : availablePirates) {
			for (Asteroid asteroid : asteroids) {
				if (pirate.canPush(asteroid)){
                    pirate.push(asteroid, (capsules.size() == 0) ? game.getEnemyCapsules()[0].location
                    : /* this option will be replaced with nearest enemy when available*/getNearestPirate(pirate,enemyPirates));
				}

				else if(pirate.getLocation().equals(asteroid.getLocation().add(asteroid.direction))){
					pirate.sail(pirate.initialLocation);
				}
				
			}
		}
	}
	
	private static Pirate getNearestPirate(GameObject obj,ArrayList<Pirate> arr){
	    int min=arr.get(0).distance(obj);
	    Pirate nearest=arr.get(0);
	    for(int i=1;i<arr.size();i++){
	        if(arr.get(i).distance(obj)<min){
	            min=arr.get(i).distance(obj);
	            nearest=arr.get(i);
	        }
	    }
	    return nearest;
	}
	
	private static Mothership getNearestMothership(GameObject obj,ArrayList<Mothership> arr){
	    int min=arr.get(0).distance(obj);
	    Mothership nearest=arr.get(0);
	    for(int i=1;i<arr.size();i++){
	        if(arr.get(i).distance(obj)<min){
	            min=arr.get(i).distance(obj);
	            nearest=arr.get(i);
	        }
	    }
	    return nearest;
	}
	
	private static Capsule getNearestCapsule(GameObject obj,ArrayList<Capsule> arr){
	    int min=arr.get(0).distance(obj);
	    Capsule nearest=arr.get(0);
	    for(int i=1;i<arr.size();i++){
	        if(arr.get(i).distance(obj)<min){
	            min=arr.get(i).distance(obj);
	            nearest=arr.get(i);
	        }
	    }
	    return nearest;
	}
	
	private static void sailToCapsule(){
	    for(int i=0;i<capsules.size();i++){
	        collectors.add(getNearestPirate(capsules.get(i),availablePirates));
	        availablePirates.remove(getNearestPirate(capsules.get(i),availablePirates));
	    }
	}
	
	private static boolean canPushBoulder(Pirate pirate){
	    for(int i=0;i<livingAsteroids.size();i++){
	        if(pirate.canPush(livingAsteroids.get(i)))
	            return true;
	    }
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
