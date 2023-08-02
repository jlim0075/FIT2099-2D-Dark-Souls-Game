package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.enums.Status;

/**
 * Special action class for players (i.e. The Unkindled) to rest if they're near a Bonfire.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #RestingAction(Actor, String)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class RestingAction extends Action {
    private Actor player;
    private String bonfireName;


    /**
     * Constructor
     * @param player The player near to a Bonfire.
     * @param bonfireName Name of the Bonfire that the player rests at.
     */
    public RestingAction(Actor player, String bonfireName) {
        this.player = player;
        this.bonfireName = bonfireName;
    }


    /**
     * Heals the player completely, resets all instances in the game and finally return a message of the action.
     * @param actor The player near to a Bonfire.
     * @param map The map of the current actor's location.
     * @return a String describing the resting action done by the player.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Runs and resets all instances registered by various classes.
        actor.addCapability(Status.RESTING);
        BonfireManager.setLastInteractedBonfire(this.bonfireName);
        ResetManager.getInstance().run();
        return actor + " rested at " + this.bonfireName + " bonfire.";
    }

    /**
     * Provides a proper action description for the players who want to rest at a Bonfire.
     * @param actor The player resting at Bonfire.
     * @return a String describing the resting action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Rest at " + this.bonfireName + " bonfire.";
    }
}
