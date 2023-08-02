package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
		this.addCapability(Status.SAFE_ZONE);
	}

	@Override
	public void tick(Location location){
		super.tick(location);
		if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
			location.getActor().addCapability(Status.ON_SAFE_ZONE);
		}

	}


}
