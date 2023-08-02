package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.interfaces.TradableWeaponItem;

import java.util.List;

/**
 * Special action class for players (i.e. The Unkindled) to rest if they're near a Bonfire.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #TradingAction(TradableWeaponItem)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class TradingAction extends Action {
    private TradableWeaponItem weapon; // Weapons that are tradable.

    /**
     * Constructor
     * @param weapon Weapons that are tradable (e.g. Broadsword, Giant Axe and Storm Ruler).
     */
    public TradingAction(TradableWeaponItem weapon) {
        this.weapon = weapon;
    }

    /**
     * Heals the player completely, resets all instances in the game and finally return a message of the action.
     * @param actor The player trading.
     * @param map The map of the current actor's location.
     * @return a String describing the trading action done (either successful or not successful)
     * by the player and the choice of weapon.
     */
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        String executeString;
        SwapWeaponAction swapWeapons;

        // If the player has enough souls for the desired weapon (i.e. more than or equal to the cost of souls)
        // Swap the current weapon in player's item with the recently traded weapon.
        if (player.getSouls() >= (this.weapon.getCost())){
            executeString = "Player has successfully purchased " + weapon.getName() + ". Have a pleasant adventure!\n";
            swapWeapons = new SwapWeaponAction((WeaponItem) weapon);
            executeString = executeString + swapWeapons.execute(actor, map);
            List<Item> otherActorInventory = player.getInventory();
            for (Item item:otherActorInventory){
                if(item.hasCapability(Abilities.TRADABLE_CINDER_OF_YHORM) && this.weapon.getName() == "Yhorm's Great Machete"){
                    player.removeItemFromInventory(item);
                }
                if(item.hasCapability(Abilities.TRADABLE_CINDER_OF_ALDRICH) && this.weapon.getName() == "Darkmoon Longbow"){
                    player.removeItemFromInventory(item);
                }
            }
            // Subtract the player's soul by the cost of the weapon
            player.subtractSouls(weapon.getCost());
        }

        else{
            // If the player doesn't have enough souls for the desired weapon, construct an appropriate message.
            executeString = "Player does not have enough souls to purchase " + weapon.getName() + ".";
        }
        return executeString;
    }

    /**
     * Provides a proper action description for the players who want to trade with a Vendor.
     * @param actor The player trading with a vendor.
     * @return a String describing the trading action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + weapon.getName() + " (" + weapon.getCost() + " souls)";
    }
}
