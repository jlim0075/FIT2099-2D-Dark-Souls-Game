package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		WeaponItem weapon = (WeaponItem) actor.getWeapon();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
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
			for (Action drop : dropActions)
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
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}
}
