package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Lighttable;

import java.util.Objects;

/**
 * A spot for players (i.e. The Unkindled) to heal themselves and reset their Estus flask charges as well as the
 * surroundings.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #Bonfire()
 *  @see #setBonfireName(String)
 *  @see #allowableActions(Actor, Location, String)
 *  @see #lit()
 */
public class Bonfire extends Ground implements Lighttable {
    private String bonfireName;
    private Boolean lit = false;

    /**
     * Constructor for a Bonfire initialised as 'B'
     */
    public Bonfire() {
        super('B');
    }

    public void setBonfireName(String bonfireName) {
        this.bonfireName = bonfireName;
    }

    /**
     * Returns allowable actions to the actors (which in this case is the Player and the action is resting at Bonfire).
     * @param actor The player adjacent or on top of the Bonfire
     * @param location The current actor's location.
     * @return a String describing the resting action executed.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction){
        // If the bonfire is lit...
        if (this.lit){

            Actions actions = new Actions();
            actions.add(new RestingAction(actor, this.bonfireName));
            for (String i : BonfireManager.getRestablelist().keySet()) {
                if (!Objects.equals(i, this.bonfireName) && BonfireManager.getRestablelist().get(i).getGround().hasCapability(Status.INTERACTED)){
                    actions.add(new TeleportAction(BonfireManager.getRestablelist().get(i), i));
                }
            }
            return actions;
        }
        else{
            return new Actions(new LightingAction(this));
        }
    }

    /**
     * Lits (or interact with) the bonfire and set the current Bonfire to be the last place  to be interacted with.
     */
    @Override
    public void lit(){
        this.lit = true;
        BonfireManager.setLastInteractedBonfire(this.bonfireName);
    }

    @Override
    public void tick(Location location){
        super.tick(location);
        if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            location.getActor().removeCapability(Status.ON_SAFE_ZONE);
        }
    }
}
