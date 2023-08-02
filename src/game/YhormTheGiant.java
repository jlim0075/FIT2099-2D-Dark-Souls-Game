package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;

public class YhormTheGiant extends LordOfCinder implements Soul {
    private ArrayList<Behaviour> behaviours = new ArrayList<>();
    private int soulAmount;

    /**
     * Constructor.
     *
     * @param name
     * @param displayChar
     * @param hitPoints
     */
    public YhormTheGiant(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_ENEMY);
        this.addCapability(Status.LORDS_OF_CINDER);
        this.addCapability(Abilities.EMBERFORM);
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
        if (!isConscious()){
            map.locationOf(this).addItem(new CinderOfLord(Abilities.TRADABLE_CINDER_OF_YHORM));
            map.removeActor(this);
            display.println("LORD OF CINDER FALLEN");
            return new DoNothingAction();
        }

        if (this.hasCapability(Status.STUNNED)){
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }
        if (this.hasCapability(Abilities.EMBERFORM)){
            if (hitPoints < maxHitPoints/2){
                this.removeCapability(Abilities.EMBERFORM);
                Location vision = map.locationOf(this);
                for (Exit exit :vision.getExits()){
                    Location destination = exit.getDestination();
                    if (destination.containsAnActor() && destination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
                        return this.getWeapon().getActiveSkill(destination.getActor(), exit.getName()); } } }
        }
        if (actions != null){
            for (Action action : actions){
                if (action instanceof AttackAction){
                    return action;
                }
            }
        }
        for(Behaviour Behaviour : behaviours) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }

        return super.playTurn(actions,lastAction,map,display);
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
