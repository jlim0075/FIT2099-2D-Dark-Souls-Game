package game;

import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Abilities;
import game.interfaces.TradableWeaponItem;

public class Broadsword extends WeaponItem implements TradableWeaponItem {
    private final static int cost = 500; // Initialised a cost constant for Broadsword
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public Broadsword(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        super.portable = false;
        this.addCapability(Abilities.CRITICAL_STRIKE);
    }

    @Override
    public int damage(){
        return damage;
    }

    @Override
    public int getCost(){
        return cost;
    }

    @Override
    public String getName(){
        return this.name;
    }


}
