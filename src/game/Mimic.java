package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Resettable;
import game.interfaces.Soul;

import java.util.Random;

/**
 * Class representing the Mimic Chest.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see Mimic(Location, Chest)
 * @see #playTurn(Actions, Action, GameMap, Display)
 * @see #getAllowableActions(Actor, String, GameMap)
 */
public class Mimic extends Actor implements Resettable, Soul {
    private Behaviour behaviours;
    private final int soulAmount;
    private final Location chestLocation;
    private final Chest chestBeingOpened;

    /**
     * Constructor.
     *
     * @param chestLocation        Location of the chest before it was turned into a Mimic
     * @param chestBeingOpened  The Chest instance that was opened by the player.
     */
    public Mimic(Location chestLocation, Chest chestBeingOpened) {
        super("Mimic", 'M', 100);
        this.addCapability(Status.HOSTILE_ENEMY);
        this.addCapability(Abilities.KICK_THE_CRAP_OUT_OF_YOU);
        this.behaviours = new WanderBehaviour();
        this.soulAmount = 200;
        this.chestLocation = chestLocation;
        this.chestBeingOpened = chestBeingOpened;
        this.inventory.add(new LegKicking());
        registerInstance();
    }

    /**
     * At the moment, we only make it can be attacked by enemy that has HOSTILE capability
     * You can do something else with this method.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_ENEMY
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.MINDFUL_ENTITY) && !(otherActor.hasCapability(Status.DISARMED))) {
            actions.add(new AttackAction(this,direction));
        }
        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Random rand = new Random();
        if (!this.isConscious()){

        TokenOfSouls token = this.chestBeingOpened.getTokenOfSouls();
        token.addCapability(Status.TREASURED_SOULS);
        token.addToken(this.chestLocation);
        for (int i = 0; i < 2; i++){
            if (rand.nextBoolean()) {
                token.addToken(this.chestLocation);
                }
            }
        }

        Location vision = map.locationOf(this);
        for (Action action : actions){
            if (action instanceof AttackAction){
                for (Exit exit :vision.getExits()){
                    Location destination = exit.getDestination();
                    if (destination.containsAnActor() && destination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
                        if (!destination.getActor().hasCapability(Status.ON_SAFE_ZONE)){
                            this.behaviours = new FollowBehaviour(destination.getActor());
                            return action;
                        }
                        else{
                            break;
                        }
                    }
                }

            }
        }

        this.behaviours = toFollowOrWander(vision);
        return this.behaviours.getAction(this, map);
    }

    public Behaviour toFollowOrWander(Location vision){
        Behaviour behaviour = null;
        for (Exit exit :vision.getExits()){
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
                // If the player on a floor...
                if (!destination.getActor().hasCapability(Status.ON_SAFE_ZONE)){
                    behaviour = new FollowBehaviour(destination.getActor());
                }
            }
            for (Exit furtherExit :exit.getDestination().getExits()){
                Location furtherDestination = furtherExit.getDestination();
                if (furtherDestination.containsAnActor() && furtherDestination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
                    // If the player is not on a floor...
                    if (!(furtherDestination.getActor().hasCapability(Status.ON_SAFE_ZONE)) && (this.behaviours instanceof FollowBehaviour)){
                        behaviour = new FollowBehaviour(furtherDestination.getActor());
                    }
                }
            }
        }
        if (behaviour == null){
            behaviour = new WanderBehaviour();
        }
        return behaviour;
    }

    @Override
    public void resetInstance() {
        if (this.isConscious()){
            this.chestLocation.map().removeActor(this);
        }
        this.chestLocation.setGround(new Chest());
    }

    @Override
    public boolean isExist() {
        return true;
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulAmount);

    }

    @Override
    public int getSouls() {
        return soulAmount;
    }


    @Override
    public String toString() {
        if (getWeapon() instanceof IntrinsicWeapon){
            return name + "(" + hitPoints +
                    "/"  + maxHitPoints + ")(Holding Nothing)";
        }
        else {
            return name + "(" + hitPoints +
                    "/"  + maxHitPoints + ")(Holding " + getWeapon() + ")";
        }

    }
}
