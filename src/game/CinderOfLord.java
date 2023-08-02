package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

/**
 * Class representing the Cinder of Lord.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see CinderOfLord
 * @see #tick(Location)
 * @see #tick(Location)
 */
public class CinderOfLord extends Item {
    private PickUpItemAction pickUpItemAction;
    /**
     * Constructor of CinderOfLord class while adding a status to signify it is whichever Lord's Cinder.
     */
    public CinderOfLord(Enum abilities) {
        super("Cinder Of Lord", '*', false);
        this.pickUpItemAction = new PickUpItemAction(this);
        this.addCapability(abilities);
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        // Ensures the player doesn't have redundant options (i.e. Just one action is enough).
        if (currentLocation.containsAnActor() && currentLocation.getActor().hasCapability(Status.MINDFUL_ENTITY)){

            if (!currentLocation.getActor().getInventory().contains(this)){
                this.allowableActions.add(this.pickUpItemAction);
            }
            else{
                this.allowableActions.remove(this.pickUpItemAction);
            }
        }
        else{
            this.allowableActions.remove(this.pickUpItemAction);
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        // Ensures the player doesn't have redundant options (i.e. Just one action is enough).
        super.tick(currentLocation, actor);
        if (currentLocation.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            this.allowableActions.remove(this.pickUpItemAction);
        }

    }
}
