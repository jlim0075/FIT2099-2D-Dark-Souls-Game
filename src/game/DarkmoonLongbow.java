package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.TradableWeaponItem;

import java.util.ArrayList;

/**
 *
 * A ranged weapon that can attack from a distance
 *
 */
public class DarkmoonLongbow extends WeaponItem implements TradableWeaponItem {

    /**
     * Constructor to make a Longbow object.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public DarkmoonLongbow(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
        this.addCapability(Abilities.CRITICAL_STRIKE);
        this.addCapability(Abilities.RANGED_WEAPON);
    }


    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     * On each tick,will check on actors location if any enemy is in range,where it will add shooting action to
     * allowable actions.On each tick,will clear the allowable actions list to prevent old actions from appearing
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        // Ranger's data
        Actor ranger = actor;
        Enum rangerStatus;
        this.allowableActions.clear();
        if (ranger.hasCapability(Status.HOSTILE_ENEMY)){
            rangerStatus = Status.HOSTILE_ENEMY;
        }
        else {
            rangerStatus = Status.MINDFUL_ENTITY;
        }
        int rangerXCoordinates = currentLocation.x();
        int rangerYCoordinates = currentLocation.y();

        // Target's data
        ArrayList<Actor> target = new ArrayList<Actor>();

        for (int xAxis = -3 ; xAxis < 4 ; xAxis++){
            for (int yAxis = -3 ; yAxis < 4 ; yAxis++){
                if (xAxis != 0 && yAxis != 0){
                    int x = rangerXCoordinates + xAxis;
                    int y = rangerYCoordinates + yAxis;
                    if (x < currentLocation.map().getXRange().max()+1 && x >= 0 ) {
                        if (y < currentLocation.map().getYRange().max()+1 && y >= 0 ) {
                            if (currentLocation.map().at(x,y).containsAnActor()){
                                Enum targetStatus;
                                if (currentLocation.map().at(x,y).getActor().hasCapability(Status.HOSTILE_ENEMY)){
                                    targetStatus = Status.HOSTILE_ENEMY;
                                }
                                else {
                                    targetStatus = Status.MINDFUL_ENTITY;
                                }

                                if (targetStatus != rangerStatus){
                                    target.add(currentLocation.map().at(x,y).getActor());
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Actor existingTargets: target){
            Location locationOfExistingTarget = currentLocation.map().locationOf(existingTargets);
            String direction = getDirection(locationOfExistingTarget.x(), locationOfExistingTarget.y(), rangerXCoordinates, rangerYCoordinates);

            if (!didRangerMissed(currentLocation, locationOfExistingTarget, currentLocation.map())){
                this.allowableActions.add(new ShootingAction(existingTargets, direction, false));
            }
            else{
                this.allowableActions.add(new ShootingAction(existingTargets, direction, true));
            }
        }

    }


    /**
     * Checks for the direction of where the target is
     *
     * @param targetXCoordinates x coordinates of target
     * @param targetYCoordinates y coordinates of target
     * @param rangerXCoordinates x coordinates of longbow holder
     * @param rangerYCoordinates y coordinates of longbow holder
     * @return The direction of the target
     */
    public String getDirection(int targetXCoordinates, int targetYCoordinates, int rangerXCoordinates, int rangerYCoordinates){
        String direction = "";
        if (targetYCoordinates < rangerYCoordinates){
            direction = "North";
        }
        else if (targetYCoordinates > rangerYCoordinates){
            direction = "South";
        }

        if (direction.equals("")){
            if (targetXCoordinates < rangerXCoordinates){
                direction = "West";
            }
            else {
                direction = "East";
            }
        }
        else {
            if (targetXCoordinates < rangerXCoordinates){
                direction = direction + "west";
            }
            else {
                direction = direction + "east";
            }
        }

        return direction;
    }

    /**
     *
     * Checks if the path to the target is blocked by an object (Such as wall or doors)
     *
     * @param rangerLocation Location of the longbow holder
     * @param targetLocation Location of the target
     * @param map The game map
     * @return A Boolean telling whether or not the ranger missed
     */
    public boolean didRangerMissed(Location rangerLocation, Location targetLocation, GameMap map){

        NumberRange horizontalRange = new NumberRange(Math.min(rangerLocation.x(), targetLocation.x()), Math.abs(rangerLocation.x() - targetLocation.x()) + 1);
        NumberRange verticalRange = new NumberRange(Math.min(rangerLocation.y(), targetLocation.y()), Math.abs(rangerLocation.y() - targetLocation.y()) + 1);

        for (int x : horizontalRange) {
            for (int y : verticalRange) {
                if(map.at(x, y).getGround().blocksThrownObjects())
                    return true;
            }
        }
        return false;
    }
}
