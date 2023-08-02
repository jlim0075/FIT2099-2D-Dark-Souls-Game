package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Status;

/**
 * Special action class for actors to release a wind slash attack action with a Storm Ruler.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #WindSlashAttackAction(StormRuler, Actor)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class WindSlashAttackAction extends WeaponAction {
    private StormRuler stormRuler;
    private Actor target;

    /**
     * Constructor.
     *
     * @param stormRuler The WeaponItem instance, Storm Ruler itself.
     * @param target The target which the wind slashing attack action will be dealing with.
     */
    public WindSlashAttackAction(StormRuler stormRuler, Actor target) {
        super(stormRuler);
        this.stormRuler = stormRuler;
        this.target = target;
    }

    /**
     * Deal twice the Storm Ruler's damage to Yhorm the Giant and return a message of the attack action done.
     * @param actor The actor equipped with a Storm Ruler.
     * @param map The map of the current actor's location.
     * @return a String describing the wind slashing attack action executed.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int slashingDamageDealt = this.stormRuler.damage() * 2;
        this.stormRuler.emptyCharges();
        this.target.hurt(slashingDamageDealt);
        this.target.addCapability(Status.STUNNED);
        this.target.addCapability(Status.STUNNED);
        return actor + " released a violent Wind Slash against Yhorm the Giant!";
    }

    /**
     * Provides a proper action description for the actors who want to perform a wind slashing attack action.
     * @param actor The actor equipped with a Storm Ruler.
     * @return a String describing the wind slashing attack action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " release wind slash against Yhorm the Giant.";
    }

}