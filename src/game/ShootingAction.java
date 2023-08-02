package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

import java.util.Random;
public class ShootingAction extends Action {
    protected Actor target;
    protected String direction;
    protected boolean didRangerMissed;
    protected Random rand = new Random();


    /**
     * Constructor to make a shooting action
     *
     * @param target The target the holder is shooting at
     * @param direction The direction of the target
     */
    public ShootingAction(Actor target,String direction, boolean didRangerMissed){
        this.target = target;
        this.direction = direction;
        this.didRangerMissed = didRangerMissed;
    }


    /**
     * When executed,will check if target is missed or not.If not will attack the player nearly similar to how attackaction
     * does
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string detailing what happened (Who attacked who)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        WeaponItem weapon = (WeaponItem) actor.getWeapon();

        if (didRangerMissed || !(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        if(weapon.hasCapability(Abilities.CRITICAL_STRIKE)){
            if (rand.nextInt(6) == 1){
                damage = damage*2;
            }
        }
        target.hurt(damage);
        if (!target.isConscious()) {
            Actions dropActions = new Actions();
            // drop all items
            for (Item item : target.getInventory())
                if (!(item instanceof WeaponItem) || !(item.hasCapability(Status.CONSUMABLE))){
                    dropActions.add(item.getDropAction(actor));}
            for(Action drop : dropActions)
                drop.execute(target, map);
            if (target instanceof Player){
                ResetManager.getInstance().run();
                result += System.lineSeparator() + """
					██╗░░░██╗░█████╗░██╗░░░██╗  ██████╗░██╗███████╗██████╗░
					╚██╗░██╔╝██╔══██╗██║░░░██║  ██╔══██╗██║██╔════╝██╔══██╗
					░╚████╔╝░██║░░██║██║░░░██║  ██║░░██║██║█████╗░░██║░░██║
					░░╚██╔╝░░██║░░██║██║░░░██║  ██║░░██║██║██╔══╝░░██║░░██║
					░░░██║░░░╚█████╔╝╚██████╔╝  ██████╔╝██║███████╗██████╔╝
					░░░╚═╝░░░░╚════╝░░╚═════╝░  ╚═════╝░╚═╝╚══════╝╚═════╝░
					Haha you stinky poopy loser pathetic poop eww *hatoof*""";
            }
            else if (actor instanceof Player){
                target.asSoul().transferSouls(actor.asSoul());
                if (!target.hasCapability(Status.LORDS_OF_CINDER)){
                    map.removeActor(target);
                }
                result += System.lineSeparator() + target + " is killed.";
            }
        }
        return result;
    }

    @Override
    public String menuDescription(Actor actor) { return actor + " shoots " + target + " at " + direction; }
}
