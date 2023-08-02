package game;

import edu.monash.fit2099.demo.mars.DemoCapabilities;
import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.TradableWeaponItem;

import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Special action class for players (i.e. The Unkindled) to teleport to another location or map.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #TeleportAction(Location, String)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class TeleportAction extends MoveActorAction {
    private String nameOfLocation;
    /**
     * Constructor
     * @param teleportToLocation The location which the player wishes to be teleported to.
     * @param nameOfLocation The name of the location which the player wishes to be teleported to.
     */
    public TeleportAction(Location teleportToLocation, String nameOfLocation){
        super(teleportToLocation, "");
        this.nameOfLocation = nameOfLocation;
    }

    /**
     * Teleports the player to his/her desired location/map.
     *
     * @param actor The player who is teleporting.
     * @param map The map which the player is teleporting to.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(Status.MINDFUL_ENTITY)){
            super.execute(actor, map);
            return actor + " teleports to " + this.nameOfLocation;
        }
        else{
            return "";
        }
    }

    /**
     * Returns a description of this executable teleportation to the display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player moves east"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves to " + this.nameOfLocation;
    }
}
