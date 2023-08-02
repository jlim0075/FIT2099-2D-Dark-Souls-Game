package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;


/**
 * The boss of Design o' Souls
 * Has attributes soulAmount containing the amount of souls it has
 * and a list containing all of its abilities and statuses
 */
public abstract class LordOfCinder extends Actor implements Soul {
    private ArrayList<Behaviour> behaviours = new ArrayList<>();
    private int soulAmount;

    /**
     * Constructor.
     */
    public LordOfCinder(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints );
        this.addCapability(Status.HOSTILE_ENEMY);
        soulAmount = 5000;
    }

    /**
     * Will check if below half health where it will increase weapon chance to hit and also set surroundings on fire
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }



    /**
     *
     * @param item The Item to add.
     */
    @Override
    public void addItemToInventory(Item item) {
        super.addItemToInventory(item);
    }


    /**
     *
     * Transfer the boss amount of souls to the soulobject (Done when the boss is killed)
     *
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulAmount);
    }


    @Override
    public int getSouls() {
        return soulAmount;
    }


    /**
     *
     * @return a String describing/detailing the Boss hitpoints and the weapon it is holding
     */
    @Override
    public String toString() {
        return name + "(" + hitPoints +
                "/" + maxHitPoints + ")(Holding " + getWeapon().toString() + ")";
    }
}
