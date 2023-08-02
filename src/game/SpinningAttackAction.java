package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.List;

/**
 * Special action class for actors to spin his/her axe and deal damage to his/her surroundings.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #SpinningAttackAction(WeaponItem)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class SpinningAttackAction extends WeaponAction {

    /**
     * Constructor
     * @param weaponItem The weapon used (i.e. Giant Axe) to perform this spinning attack action.
     */
    public SpinningAttackAction(WeaponItem weaponItem) {
        super(weaponItem);
    }

    /**
     * Deal damage to enemies (if any) at the actor's surroundings and return a message of the attack action done.
     * @param actor The actor equipped with a Giant Axe.
     * @param map The map of the current actor's location.
     * @return a String describing the spinning attack action executed.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        List<Exit> actorSurroundings = (map.locationOf(actor)).getExits();
        // Spinning attack only deals 50 per cent of the Giant Axe's damage to the surroundings.
        int spinningDamageDealt = (int) (super.weapon.damage() * 0.5);

        for (Exit exit : actorSurroundings){
            Location destination = exit.getDestination();
            // If the adjacent locations contain an actor...
            if (destination.containsAnActor()) {
                if (destination.getActor().hasCapability(Status.MINDFUL_ENTITY) || destination.getActor().hasCapability(Status.HOSTILE_ENEMY)){
                    // Deal damage to those actors
                    destination.getActor().hurt(spinningDamageDealt);
                }
            }
        }
        return actor + " performed a spinning attack with a Giant Axe at his/her surrounding ferociously";
    }

    /**
     * Provides a proper action description for the actors who want to perform a spinning attack action.
     * @param actor The actor equipped with a Giant Axe.
     * @return a String describing the spinning attack action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " perform a spinning attack with a Giant Axe.";
    }
}
