package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Special action class for players (i.e. The Unkindled) to pick up souls that they once lost.
 *  @author Hoh Zheng Jie
 *  @version 2.0.0
 *  @see #PickUpSoulsAction(TokenOfSouls, Location)
 *  @see #execute(Actor, GameMap)
 *  @see #removeSoulsOnTheGround(Location)
 *  @see #menuDescription(Actor)
 */
public class PickUpSoulsAction extends PickUpItemAction {

    private Location currentLocation;
    private int amountOfSoulsToBeRetrieved;

    /**
     * Constructor
     * @param  soulsToBeRetrieved The instance of the dropped souls on the ground.
     * @param currentLocation The location where the lost souls lie at.
     */
    public PickUpSoulsAction(TokenOfSouls soulsToBeRetrieved, Location currentLocation){
        super(soulsToBeRetrieved);
        this.currentLocation = currentLocation;
        this.amountOfSoulsToBeRetrieved = soulsToBeRetrieved.getSouls();
    }

    /**
     * Transfer the picked-up-souls back to the actor and remove the token of souls lying on the ground.
     * @param actor The actor picking up the lost souls.
     * @param map The map of the current actor's location.
     * @return a String describing the souls-picking up action being executed if there's charges left, otherwise,
     * returns null.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
            Player player = (Player) actor;
            player.addSouls(this.amountOfSoulsToBeRetrieved);
            removeSoulsOnTheGround(this.currentLocation);
        return actor + " has retrieved back his/her souls.";

    }

    /**
     * Remove the souls lying on the ground.
     * @param currentLocation The location where the lost souls lie at.
     */
    public void removeSoulsOnTheGround(Location currentLocation){
        currentLocation.removeItem(this.item);
    }

    /**
     * Provides a proper action description for the players who want to pick up his/her lost souls.
     * @param actor The actor picking up the token of souls.
     * @return a String describing the souls-picking up action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Retrieve souls (" + this.amountOfSoulsToBeRetrieved + " souls)";
    }
}