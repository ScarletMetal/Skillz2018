package bots;

import java.util.*;
import pirates.*;

public class MyBot implements PirateBot {

	private static final ArrayList<Pirate> availablePirates = new ArrayList<Pirate>();
	private static final ArrayList<Pirate> enemyPirates = new ArrayList<Pirate>();

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

		enemyPirates.addAll(Arrays.asList(game.getEnemyLivingPirates()));

		final ArrayList<Mothership> motherships = new ArrayList<Mothership>();
		motherships.addAll(Arrays.asList(game.getMyMotherships()));

		final ArrayList<Asteroid> livingAsteroids = new ArrayList<Asteroid>();
		livingAsteroids.addAll(Arrays.asList(game.getLivingAsteroids()));

		final ArrayList<Capsule> capsules = new ArrayList<Capsule>();
		capsules.addAll(Arrays.asList(game.getMyCapsules()));

		avoidBoulders(livingAsteroids, game);
		
		final ArrayList<Pirate> collectors = new ArrayList<Pirate>();
		sailToCapsule(capsules, collectors);
		
		collectorDoTurn(capsules, collectors, motherships);

		availablePirates.clear();
		livingAsteroids.clear();
		motherships.clear();
		capsules.clear();
		collectors.clear();
		enemyPirates.clear();
		
		// Try to push, if you didn't - take the capsule and go to the mothership.
	}

	private static boolean avoidBoulders(ArrayList<Asteroid> asteroids, PirateGame game) {
		boolean acted = false;
		for (int i = 0; i<availablePirates.size();i++) {
			Pirate pirate = availablePirates.get(i);
			for (Asteroid asteroid : asteroids) {
				if (pirate.canPush(asteroid)) {
					Location target;
					if(game.getEnemyCapsules().length != 0) 
						target = game.getEnemyCapsules()[0].location;
					else if(!asteroid.location.equals(asteroid.location.subtract(asteroid.direction)))
						target = asteroid.getLocation();
					else
						target = null;
					if(target != null) {
						pirate.push(asteroid,target);
						acted = true;
					}
				}if (pirate.getLocation().equals((asteroid.getLocation().add(asteroid.direction)))&&!acted) {
					pirate.sail(pirate.initialLocation);
					acted = true;
				}
				if (acted)
					availablePirates.remove(pirate);
			}
		}
		return true;
	}

	private static Pirate getNearestPirate(GameObject obj, ArrayList<Pirate> arr) {
		if (arr.size() != 0) {
			int min = arr.get(0).distance(obj);
			Pirate nearest = arr.get(0);
			for (int i = 1; i < arr.size(); i++) {
				if (arr.get(i).distance(obj) < min) {
					min = arr.get(i).distance(obj);
					nearest = arr.get(i);
				}
			}
			return nearest;
		} else
			return null;
	}

	private static Mothership getNearestMothership(GameObject obj, ArrayList<Mothership> arr) {
		int min = arr.get(0).distance(obj);
		Mothership nearest = arr.get(0);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).distance(obj) < min) {
				min = arr.get(i).distance(obj);
				nearest = arr.get(i);
			}
		}
		return nearest;
	}

	private static Capsule getNearestCapsule(GameObject obj, ArrayList<Capsule> arr) {
		int min = arr.get(0).distance(obj);
		Capsule nearest = arr.get(0);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).distance(obj) < min) {
				min = arr.get(i).distance(obj);
				nearest = arr.get(i);
			}
		}
		return nearest;
	}
	
	private static Asteroid getNearestAsteroid(GameObject obj, ArrayList<Asteroid> arr) {
		int min = arr.get(0).distance(obj);
		Asteroid nearest = arr.get(0);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).distance(obj) < min) {
				min = arr.get(i).distance(obj);
				nearest = arr.get(i);
			}
		}
		return nearest;
	}

	private static void sailToCapsule(ArrayList<Capsule> capsules, ArrayList<Pirate> collectors) {
		for (int i = 0; i < capsules.size(); i++) {
			collectors.add(getNearestPirate(capsules.get(i), availablePirates));
			availablePirates.remove(getNearestPirate(capsules.get(i), availablePirates));
		}
	}

	private static void collectorDoTurn(ArrayList<Capsule> capsules, ArrayList<Pirate> collectors,
			ArrayList<Mothership> motherships) {
	    if(collectors.get(0) != null){
		for (int i = 0; i < capsules.size(); i++) {
			if (!collectors.get(i).hasCapsule())
				collectors.get(i).sail(capsules.get(i));
			else
				collectors.get(i).sail(getNearestMothership(collectors.get(i), motherships));
	    	}
		}
	}

	
	
	private static void companionsSetUp(ArrayList<Pirate> availablePirates, ArrayList<Pirate> collectorPirates,
										PirateGame game, ArrayList<Pirate> companionPirates) {
		
		if(collectorPirates.size() !=0 && availablePirates.size()>collectorPirates.size()) {
		
			for (Pirate collectorPirate : collectorPirates) {
			
				for(int i=0; i < collectorPirates.size(); i++ ) {
				companionPirates.add(availablePirates.get(i));
				availablePirates.remove(i);
				
				}
			}
		}
	}
	
private static void companions(ArrayList<Pirate> companionPirates, ArrayList<Pirate> collectorPirates, PirateGame game) {
	
		for (Pirate pirate : collectorPirates) {
			
			
			
			
		}
		
	
	
	
	}
	
}

