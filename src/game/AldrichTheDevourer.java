package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;

public class AldrichTheDevourer extends LordOfCinder implements Soul {
    private ArrayList<Behaviour> behaviours = new ArrayList<>();
    private int soulAmount;

    /**
     * Constructor to spawn an Aldrich.
     *
     * @param name
     * @param displayChar
     * @param hitPoints
     */
    public AldrichTheDevourer(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.hasCapability(Status.HOSTILE_ENEMY);
        this.addCapability(Status.LORDS_OF_CINDER);
        soulAmount = 5000;
    }


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.MINDFUL_ENTITY) && !(otherActor.hasCapability(Status.DISARMED))) {
            actions.add(new AttackAction(this,direction));
            Weapon weapon = otherActor.getWeapon();
            WeaponAction weaponAction = weapon.getActiveSkill(this, direction);
            if(weaponAction != null){
                actions.add(weaponAction);
            }
        }
        return actions;
    }


    /**
     * Checks if still alive along with other scenarios including being stunned and being able to attack
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (!isConscious()) {
            map.locationOf(this).addItem(new CinderOfLord(Abilities.TRADABLE_CINDER_OF_ALDRICH));
            map.removeActor(this);
            display.println("LORD OF CINDER FALLEN");
            return new DoNothingAction();
        }

        if (this.hasCapability(Status.STUNNED)) {
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }
        Location vision = map.locationOf(this);
        isActorNear(vision);
        if (actions != null) {
            for (Action action : actions) {
                if (action instanceof AttackAction) {
                    return action;
                }
            }
        }
        for (Behaviour Behaviour : behaviours) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }

        return super.playTurn(actions, lastAction, map, display);
    }


    /**
     *
     * Checks if a player is within an enemy's view where it will begin following the player
     *
     *
     * @param vision The possible steps the Undead can take
     */
    public void isActorNear(Location vision){
        for (Exit exit1 : vision.getExits()) {
            Location destination1 = exit1.getDestination();

            if (destination1.containsAnActor() && destination1.getActor() instanceof Player) {
                if (!behaviours.isEmpty()) {
                    behaviours.remove(0);
                }
                behaviours.add(new FollowBehaviour(destination1.getActor())); }
            for (Exit exit2 : destination1.getExits()) {
                Location destination2 = exit2.getDestination();
                if (destination2.containsAnActor() && destination2.getActor() instanceof Player) {
                    if (!behaviours.isEmpty()) {
                        behaviours.remove(0);
                    }
                    behaviours.add(new FollowBehaviour(destination2.getActor())); }
                for (Exit exit3 : destination2.getExits()) {
                    Location destination3 = exit3.getDestination();
                    if (destination3.containsAnActor() && destination3.getActor() instanceof Player) {
                        if (!behaviours.isEmpty()) {
                            behaviours.remove(0);
                        }
                        behaviours.add(new FollowBehaviour(destination3.getActor())); } } }
        }
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


}
