package game;

import edu.monash.fit2099.engine.*;

/**
 * The gorge or endless gap that is dangerous for the Player.
 */
public class Valley extends Ground {

	public Valley() {
		super('+');
	}

	/**
	 * Only the player can enter the valley while enemies are not able to
	 *
	 * @param actor the Actor to check
	 * @return false or actor cannot enter.
	 */
	@Override
	public boolean canActorEnter(Actor actor){
		return actor instanceof Player;
	}


	/**
	 *
	 * Checks if player is on its location (simulating that player has stepped into the valley.From there,the player
	 * would be given a large amount of damage to kill the player (Since falling into the valley kills the player)
	 *
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return list of allowable actions
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		if (location.containsAnActor() && location.getActor() instanceof Player){
			actor.hurt(Integer.MAX_VALUE);
		}
		return super.allowableActions(actor, location, direction);
	}

	@Override
	public void tick(Location location){
		super.tick(location);
		if (location.containsAnActor() && location.getActor() instanceof Player){
			System.out.println("""

					██╗░░░██╗░█████╗░██╗░░░██╗  ██████╗░██╗███████╗██████╗░
					╚██╗░██╔╝██╔══██╗██║░░░██║  ██╔══██╗██║██╔════╝██╔══██╗
					░╚████╔╝░██║░░██║██║░░░██║  ██║░░██║██║█████╗░░██║░░██║
					░░╚██╔╝░░██║░░██║██║░░░██║  ██║░░██║██║██╔══╝░░██║░░██║
					░░░██║░░░╚█████╔╝╚██████╔╝  ██████╔╝██║███████╗██████╔╝
					░░░╚═╝░░░░╚════╝░░╚═════╝░  ╚═════╝░╚═╝╚══════╝╚═════╝░
					Haha you stinky poopy loser pathetic poop eww *hatoof*""");
			ResetManager.getInstance().run(); // Resets all instances registered by various classes.
		}

	}




}
