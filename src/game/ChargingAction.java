package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Status;

/**
 * Special action class for actors to charge his/her Storm Ruler.
 *  @author Hoh Zheng Jie
 *  @version 2.0.0
 *  @see #ChargingAction(StormRuler)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class ChargingAction extends WeaponAction {
    private StormRuler stormRuler;

    /**
     * Constructor.
     *
     * @param stormRuler The WeaponItem instance, Storm Ruler itself.
     */
    public ChargingAction(StormRuler stormRuler) {
        super(stormRuler);
        this.stormRuler = stormRuler;
    }

    /**
     * Charge up the Storm Ruler and disarm the player wielding it.
     * @param actor The actor equipped with a Storm Ruler.
     * @param map The map of the current actor's location.
     * @return a String describing the wind slashing attack action executed.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Charge the Storm Ruler weapon item itself by incrementing its charge by 1.
        this.stormRuler.charge();
        if (!actor.hasCapability(Status.DISARMED))
            actor.addCapability(Status.DISARMED);
        return actor + " has charged his/her Storm Ruler for his/her Wind Slash.";
    }

    /**
     * Provides a proper action description for the actors who want to perform a charging action.
     * @param actor The actor equipped with a Storm Ruler.
     * @return a String describing the charging action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " charge his/her Storm Ruler.";
    }
}