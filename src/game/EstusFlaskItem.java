package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import game.enums.Status;

/**
 * Special item class for players (i.e. The Unkindled) to heal themselves.
 *  @author Hoh Zheng Jie
 *  @version 1.1.0
 *  @see #EstusFlaskItem(Actor)
 */
public class EstusFlaskItem extends Item {
    /**
     * Constructor of EstusFlaskItem class while adding a status to signify it is a consumable (i.e. Status.CONSUMABLE).
     */
    Actor estusFlaskHolder;
    public EstusFlaskItem(Actor actor) {
        super("Estus Flask", 'E', false);
        this.estusFlaskHolder = actor;
        this.addCapability(Status.CONSUMABLE);
    }
}
