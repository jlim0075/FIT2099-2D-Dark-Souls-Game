package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Resettable;
import game.interfaces.Soul;

import java.util.List;


/**
 * Class representing the Player.
 *
 *
 *
 */
public class Player extends Actor implements Soul, Resettable {

	private final Menu menu = new Menu();
	private int soul = 0;
	private GameMap map;
	private Location prevLocation;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints,GameMap map) {
		super(name, displayChar, hitPoints);
		for (int i = 0; i < 3; i++){
			EstusFlaskItem estusFlaskItem = new EstusFlaskItem(this);
			this.addItemToInventory(estusFlaskItem);
		}
		this.addCapability(Status.MINDFUL_ENTITY);
		this.addCapability(Abilities.TRADING);
		this.map = map;
		registerInstance();
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		List<Item> inventoryOfActor = this.getInventory();
		for (Item item : inventoryOfActor){
			if (item.hasCapability(Status.CONSUMABLE)){
				actions.add(new DrinkingAction(this, item));
				break;
			}
		}

		// Handle multi-turn Actions
		if (!isConscious()){
			ResetManager.getInstance().run(); // Resets all instances registered by various classes.
			display.println("""

					██╗░░░██╗░█████╗░██╗░░░██╗  ██████╗░██╗███████╗██████╗░
					╚██╗░██╔╝██╔══██╗██║░░░██║  ██╔══██╗██║██╔════╝██╔══██╗
					░╚████╔╝░██║░░██║██║░░░██║  ██║░░██║██║█████╗░░██║░░██║
					░░╚██╔╝░░██║░░██║██║░░░██║  ██║░░██║██║██╔══╝░░██║░░██║
					░░░██║░░░╚█████╔╝╚██████╔╝  ██████╔╝██║███████╗██████╔╝
					░░░╚═╝░░░░╚════╝░░╚═════╝░  ╚═════╝░╚═╝╚══════╝╚═════╝░
					Haha you stinky poopy loser pathetic poop eww *hatoof*""");
		}

		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();


		// Returns/prints the console menu
		prevLocation = map.locationOf(this);
		display.println(name + "(" + hitPoints + "/" + maxHitPoints + ")(Holding " + getWeapon() +")(" + soul +" souls)");
		return menu.showMenu(this, actions, display);
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		// it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
		if(otherActor.hasCapability(Status.HOSTILE_ENEMY)) {
				actions.add(new AttackAction(this,direction));
				Weapon weapon = otherActor.getWeapon();
				WeaponAction weaponAction = weapon.getActiveSkill(this, direction);
				if(weaponAction != null){
					actions.add(weaponAction);
				}
		}
		return actions;
	}

	public int getHP(){
		return this.hitPoints;
	}

	public int getMaxHP(){
		return this.maxHitPoints;
	}

	@Override
	public boolean isExist() {
		return true;
	}


	@Override
	public void resetInstance() {
		// Move player back to his/her initial spawning point (i.e. On top of the Bonfire).
		if (!this.hasCapability(Status.RESTING)){
			map.moveActor(this,BonfireManager.getLastInteractedBonfire());
			hitPoints = maxHitPoints;
			TokenOfSouls token = new TokenOfSouls(this.prevLocation,this.asSoul());
			token.addToken(prevLocation);
			this.soul = 0;
		}
		else{
			this.removeCapability(Status.RESTING);
			hitPoints = maxHitPoints;
		}

		// Get the inventory of the current player.
		List<Item> inventoryOfActor = this.getInventory();
		int amountOfEstusFlaskChargesLeft = 0;

		// Loop through all the items in the player's inventory.
		for (Item item : inventoryOfActor){
			// If the current iterated item is a consumable (i.e. Only can be an Estus Flask)...
			if (item.hasCapability(Status.CONSUMABLE)){
				// Increment the amount of estus flask charges left for the player.
				amountOfEstusFlaskChargesLeft++;
			}
		}

		// Calculate the amount of Estus Flask needed to be refilled.
		int amountNeededToRefillCharges = 3 - amountOfEstusFlaskChargesLeft;
		int i = 0;

		// While the Estus Flasks are not refilled...
		while (i < amountNeededToRefillCharges){
			// Add an Estus Flask Item to the player's inventory.
			EstusFlaskItem estusFlaskItem = new EstusFlaskItem(this);
			this.addItemToInventory(estusFlaskItem);
			i++;
		}
	}



	/**
	 * Adds an amount of souls to the already existing amount of souls the player has
	 *
	 * @param souls number of souls to be incremented.
	 * @return true if the souls has been added
	 */
	@Override
	public boolean addSouls(int souls) {
		soul += souls;
		return true;
	}

	/**
	 * Decrement an amount of souls to the already existing amount of souls the player has
	 *
	 * @param souls number of souls to be decremented.
	 * @return true if the souls has been subtracted
	 */
	@Override
	public boolean subtractSouls(int souls){
		soul -= souls;
		return true;
	}

	@Override
	public int getSouls() {
		return this.soul;
	}


	/**
	 * Transfer the players amount of souls to the soulobject (Done when player dies)
	 *
	 *
	 * @param soulObject a target souls.
	 */
	@Override
	public void transferSouls(Soul soulObject) {
		//TODO: transfer Player's souls to another Soul's instance.
		soulObject.addSouls(soul);
	}
}
