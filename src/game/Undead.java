package game;


import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

/**
 * An undead minion.
 * Attributes:
 * - ArrayList Behaviour (A list containing all the behaviours of an Undead such as wandering and following)
 * - soulAmount (containing the amount of souls it has)
 *
 */
public class Undead extends Actor implements Soul {
	// Will need to change this to a collection if Undeads gets additional Behaviours.
	private Behaviour behaviours;
	private int soulAmount;
	/**
	 * Constructor.
	 * All Undeads are represented by an 'u' and have 30 hit points.
	 * @param name the name of this Undead
	 */
	public Undead(String name) {
		super(name, 'u', 50);
		this.addCapability(Status.HOSTILE_ENEMY);
		this.addCapability(Status.ENEMY_DIE_INSTANTLY);
		this.behaviours = new WanderBehaviour();
		soulAmount = 50;
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
			actions.add(new AttackAction(this, direction));
		}
		return actions;
	}

	/**
	 * When not following player,will die instantly if it reaches a 10% chance.Also checks during playturn if player is
	 * on one of its possible positions to move where it will start following the player.
	 *
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (!(this.behaviours instanceof FollowBehaviour)){
			if (this.hasCapability(Status.ENEMY_DIE_INSTANTLY)){
				Random rand = new Random();
				int chance = rand.nextInt(11);
				if (chance == 1 ){
					map.removeActor(this);
					display.println("Undead has died");
					return new DoNothingAction();
				}
			}
		}
		Location vision = map.locationOf(this);
		for (Action action : actions){
			if (action instanceof AttackAction){
				for (Exit exit :vision.getExits()){
					Location destination = exit.getDestination();
					if (destination.containsAnActor() && destination.getActor().hasCapability(Status.MINDFUL_ENTITY)){
						if (!destination.getActor().hasCapability(Status.ON_SAFE_ZONE) || !destination.getGround().hasCapability(Status.SAFE_ZONE)){
							this.behaviours = new FollowBehaviour(destination.getActor());
						}
					}
				}
				return action;
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


	/**
	 *
	 * Used when the Undead wants to attack back
	 *
	 * @return A new weapon that comes from the player (Punching,Kicking..etc)
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(20,"punches") ;
	}

	/**
	 *
	 * Transfer Undeads soul amount to a soul object (Done when Undead is killed)
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

	/**
	 *
	 *
	 * @return A string detailing on the Undeads hitpoints
	 */
	@Override
	public String toString() {
		return name + "(" + hitPoints + '/' + maxHitPoints +')';
	}





}

