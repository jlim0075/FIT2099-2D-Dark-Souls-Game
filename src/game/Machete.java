package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Status;
import game.interfaces.TradableWeaponItem;

import java.util.List;

public class Machete extends WeaponItem implements TradableWeaponItem {
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public Machete(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        super.portable = false;
        this.allowableActions.add(new BurnGroundAction(this));

    }


    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getName() {
        return super.name;
    }

    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        return new BurnGroundAction(this);
    }
}
