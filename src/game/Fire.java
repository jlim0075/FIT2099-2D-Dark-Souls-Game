package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;


/**
 *
 *
 * The fire type ground that can damage the player and only lasts for 3 turns
 *
 */
public class Fire extends Ground {
    private int count;


    /**
     *
     * Constructor
     *
     */
    public Fire(){
        super('V');
        count = 0;
    }


    /**
     * Adds one to the counter until 3 where it will extinguish and become the ground
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            location.getActor().removeCapability(Status.ON_SAFE_ZONE);
        }
        count += 1;
    }

    /**
     *
     * Checks if it has been 3 (in this case 4) turns or not where it will extinguish and become the ground
     * Will only hurt actors that are not the boss itself
     *
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return Actions that can be done
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        if (count == 4){
            location.setGround(new Dirt());

        }
        if (location.containsAnActor()){
           if (!(location.getActor() instanceof LordOfCinder)){
               actor.hurt(25);
            }
        }

        return super.allowableActions(actor, location, direction);
    }
}
