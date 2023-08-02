package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
	}

	@Override
	public void tick(Location location){
		super.tick(location);
		if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
			location.getActor().removeCapability(Status.ON_SAFE_ZONE);
		}
	}
}
