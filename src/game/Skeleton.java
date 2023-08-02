package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.Random;


public class Skeleton extends Actor implements  Soul{
    // Will need to change this to a collection if Undeads gets additional Behaviours.
    private Behaviour behaviours;
    private int soulAmount;

    /**
     * Constructor.
     * All Undeads are represented by an 'u' and have 30 hit points.
     * @param name the name of this Undead
     */
    public Skeleton(String name) {
        super(name, 's', 100);
        this.addCapability(Status.HOSTILE_ENEMY);
        this.addCapability(Abilities.RESURRECT);
        this.behaviours = new WanderBehaviour();
        soulAmount = 250;
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

    /**
     * Checks if alive on playturn where it will either resurrect or actually die.Also checks if player is around to
     * start following
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Random rand = new Random();
        if (!this.isConscious())
            if (this.hasCapability(Abilities.RESURRECT) && rand.nextInt(2) == 1) {
                this.hitPoints = 100;
                this.removeCapability(Abilities.RESURRECT);
                display.println("Skeleton has resurrected");
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

    /**
     *
     * Checks if a player is within an enemy's view where it will begin following the player.
     * Otherwise, the enemy would just wander around
     *
     *
     * @param vision The adjacent squares of the skeleton, representing its vision.
     */
    public Behaviour toFollowOrWander(Location vision){
        Behaviour behaviour = null;
        for (Exit exit :vision.getExits()){
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
                if (!destination.getActor().hasCapability(Status.ON_SAFE_ZONE)){
                    behaviour = new FollowBehaviour(destination.getActor());
                }
            }
            for (Exit furtherExit :exit.getDestination().getExits()){
                Location furtherDestination = furtherExit.getDestination();
                if (furtherDestination.containsAnActor() && furtherDestination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
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
