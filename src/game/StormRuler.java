package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.TradableWeaponItem;

import java.util.List;

public class StormRuler extends WeaponItem implements TradableWeaponItem {
    private final static int cost = 2000;
    private int chargedAmount = 0;
    private ChargingAction chargingAction;
    private SwapWeaponAction swapWeaponAction;

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public StormRuler(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        super.portable = false;
        this.chargingAction = new ChargingAction(this);
        this.swapWeaponAction = new SwapWeaponAction(this);
        this.addCapability(Abilities.CRITICAL_STRIKE);
    }

    @Override
    public int getCost(){
        return cost;
    }

    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Get the active skill of the Storm Ruler (specifically the Wind Slash action only).
     * @param target The target which the action will be dealing with.
     * @param direction The direction which the action will be dealing the target with.
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction){
        if (chargedAmount == 3) {
            for (Action action: this.allowableActions.getUnmodifiableActionList()){
                if (action instanceof WindSlashAttackAction){
                    return null;
                }
            }
            this.allowableActions.remove(this.chargingAction);
            return new WindSlashAttackAction(this, target);
        }
        return null;
    }

    /**
     * Checks if the Storm Ruler is charged fully or not throughout the passage of time/turns,
     * so that it can be determined if a charging action should be initialised into the player.
     * @param actor The actor equipped with a Storm Ruler.
     * @param currentLocation Location of the actor equipped with a Storm Ruler.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (chargedAmount != 3 && !hasInstanceOfChargingAction(this.allowableActions.getUnmodifiableActionList()))
            this.allowableActions.add(this.chargingAction);
        if (chargedAmount == 3 && hasInstanceOfChargingAction(this.allowableActions.getUnmodifiableActionList()))
            this.allowableActions.remove(this.chargingAction);
            actor.removeCapability(Status.DISARMED);
        if (currentLocation.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            this.allowableActions.remove(this.swapWeaponAction);
        }

    }

    /**
     * Allows the Storm Ruler in the chamber be picked up by the players.
     * @param location Location where the Storm Ruler lies.
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (location.containsAnActor() && location.getActor().hasCapability(Status.MINDFUL_ENTITY)){
            List<Item> itemInInventory = location.getItems();
            for (Item item: itemInInventory){
                if ((item instanceof StormRuler)){
                    this.allowableActions.add(this.swapWeaponAction);
                }
            }

        }

    }

    /**
     * Reset the charges of the Storm Ruler back to zero.
     */
    public void emptyCharges(){
        chargedAmount = 0;
    }

    /**
     * Increment the charges of the Storm Ruler
     */
    public void charge(){
        chargedAmount ++;
    }

    /**
     * Checks if the Storm Ruler is charged fully or not throughout the passage of time/turns,
     * so that it can be determined if a charging action should be initialised into the player.
     * @see ChargingAction
     * @param allowableActions The allowable actions in the weapon item itself.
     * @return true if the weapon item itself has an instance of ChargingAction class, otherwise false.
     */
    public boolean hasInstanceOfChargingAction(List<Action> allowableActions){
        for (Action action: allowableActions){
            if (action instanceof ChargingAction)
                return true;
        }
        return false;
    }
}
