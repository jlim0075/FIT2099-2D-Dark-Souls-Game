package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.TradableWeaponItem;

import java.util.ArrayList;
import java.util.List;

public class GiantAxe extends WeaponItem implements TradableWeaponItem {
    private final static int cost = 1000; // Initialised a cost constant for a Giant Axe

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public GiantAxe(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        super.portable = false;
        this.allowableActions.add(new SpinningAttackAction(this));
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        return new SpinningAttackAction(this);
    }
}



