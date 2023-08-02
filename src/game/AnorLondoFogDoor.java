package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

/**
 * Class representing the Vendor (i.e. The Fire Keeper).
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see Vendor(String, char, int)
 * @see #playTurn(Actions, Action, GameMap, Display)
 * @see #getAllowableActions(Actor, String, GameMap)
 */
public class AnorLondoFogDoor extends FogDoor{
    private final String entranceTo = "Profane Capital";
    private Actor playerStanding;


    public AnorLondoFogDoor() {
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            playerStanding = location.getActor();
        }
        else {
            playerStanding = null;
        }
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction){
        if (playerStanding != null && playerStanding.hasCapability(Status.MINDFUL_ENTITY)){
            int borderOfTheMap = MapManager.getMap(entranceTo).getYRange().max();
            Location locationToBeTeleportedTo = MapManager.getMap(entranceTo).at(location.x(), borderOfTheMap);
            return new Actions(new TeleportAction(locationToBeTeleportedTo, entranceTo));
        }
        return new Actions();
    }

}
