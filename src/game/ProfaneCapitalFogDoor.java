package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

public class ProfaneCapitalFogDoor extends FogDoor{
    private final String entranceTo = "Anor Londo";
    private Actor playerStanding;


    public ProfaneCapitalFogDoor() {
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
            Location locationToBeTeleportedTo = MapManager.getMap(entranceTo).at(location.x(),0);
            return new Actions(new TeleportAction(locationToBeTeleportedTo, entranceTo));
        }
        return new Actions();
    }

}
