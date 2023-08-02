package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.enums.Status;

import java.util.List;

/**
 * Special action class for players (i.e. The Unkindled) to drink an Estus Flask and heal themselves.
 *  @author Hoh Zheng Jie
 *  @version 3.0.0
 *  @see #DrinkingAction(Actor, Item)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 *  @see #hotkey()
 */
public class DrinkingAction extends Action {
    final static double healPercentage = 0.4;
    private Actor estusFlaskHolder;
    private Item estusFlaskItem;

    /**
     * Constructor
     * @param estusFlaskHolder The actor that is holding an / several Estus Flask Item.
     * @param  estusFlaskItem One of the or the only Estus Flask which the player is holding.
     * @see EstusFlaskItem
     */
    public DrinkingAction(Actor estusFlaskHolder, Item estusFlaskItem) {
        this.estusFlaskHolder = estusFlaskHolder;
        this.estusFlaskItem = estusFlaskItem;
    }

    /**
     * Charge up the Storm Ruler and disarm the player wielding it.
     * @param actor The player which can equip himself/herself with Estus Flasks.
     * @return The amount of Estus Flask the player has left.
     */
    public int amountOfEstusFlaskChargesLeft(Actor actor){
        int amountOfEstusFlaskChargesLeft = 0;
        List<Item> inventoryOfActor = actor.getInventory();
        for (Item item : inventoryOfActor){
            if (item.hasCapability(Status.CONSUMABLE)){
                amountOfEstusFlaskChargesLeft++;
            }
        }
        return amountOfEstusFlaskChargesLeft;
    }

    /**
     * Heals the player according his/her maximum hit points (40 per cent of it) and return a message of the action
     * done, then remove an Estus Flask from the player's inventory.
     * @param actor The actor using the Estus Flask item.
     * @param map The map of the current actor's location.
     * @return a String describing the drinking action executed if there's charges left, otherwise, returns null.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        int maxHitPoints = player.getMaxHP();
        int hitPoints = player.getHP();
        int hitPointsCanBeHealed = (int) (maxHitPoints * healPercentage);
        actor.heal(hitPointsCanBeHealed);
        actor.removeItemFromInventory(this.estusFlaskItem);
        return actor + " (" + hitPoints + "/" + maxHitPoints + ")" + " drank an Estus Flask.";
    }

    /**
     * Provides a proper action description for the players who want to consume an Estus Flask.
     * @param actor The actor using the Estus Flask item.
     * @return a String describing the drinking action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drinks Estus Flask (" + amountOfEstusFlaskChargesLeft(actor) + " / 3)";
    }

}
