package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

import java.util.List;

/**
 * Class representing the Vendor (i.e. The Fire Keeper).
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see Vendor(String, char, int)
 * @see #playTurn(Actions, Action, GameMap, Display)
 * @see #getAllowableActions(Actor, String, GameMap)
 */
public class Vendor extends Actor {

    /**
     * Constructor.
     *
     * @param name Name to call the Vendor in the UI
     */
    public Vendor(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints );
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Return allowable trading actions for various weapons (i.e. Broadsword, Giant Axe and Storm Ruler),
     * for actors with TRADING capability.
     * @param otherActor the player who would be trading.
     * @param direction  String representing the direction of the player.
     * @param map        current GameMap.
     * @return list of actions
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if(otherActor.hasCapability(Abilities.TRADING)) {
            actions.add(new TradingAction(new Broadsword("Broadsword",'b',30,"slashes",80)));
            actions.add(new TradingAction(new GiantAxe("GiantAxe", 'g', 50, "slashes", 80)));
            actions.add(new TradingAction(new StormRuler("Storm Ruler",'7',70,"strikes",60)));
            List<Item> otherActorInventory = otherActor.getInventory();
            for (Item item:otherActorInventory){
                if(item.hasCapability(Abilities.TRADABLE_CINDER_OF_YHORM)){
                    actions.add(new TradingAction(new Machete("Yhorm's Great Machete",'M',95, "slashes",60)));
                }
                if(item.hasCapability(Abilities.TRADABLE_CINDER_OF_ALDRICH)){
                    actions.add(new TradingAction(new DarkmoonLongbow("Darkmoon Longbow",'l',70,"shoots",80)));
                }
            }
        }
        return actions;
    }
}
