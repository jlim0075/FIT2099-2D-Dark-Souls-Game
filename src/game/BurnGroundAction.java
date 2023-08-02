package game;

import edu.monash.fit2099.engine.*;


/**
 *
 * Action that spawns fire
 */
public class BurnGroundAction extends  WeaponAction {
    private Actor actor;
    private GameMap map;


    /**
     * Constructor
     *
     *

     */
    public BurnGroundAction(WeaponItem weaponItem){
        super(weaponItem);
    }

    /**
     * Executes by adding fire to any ground actors can walk on
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location vision = map.locationOf(actor);
        for (Exit exit :vision.getExits()){
            if (exit.getDestination().canActorEnter(actor) && !exit.getDestination().containsAnActor()){
                exit.getDestination().setGround(new Fire());
            }
        }
        return actor + "has set the ground on fire!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " burns the ground with " + weapon.asWeapon().toString();
    }


}
