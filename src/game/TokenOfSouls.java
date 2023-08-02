package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Soul;

import java.util.List;


/**
 * Item left behind when player dies containing attribute soulAmount (containing the amount of souls it has all from the
 * player)
 *
 */
public class TokenOfSouls extends PortableItem implements Soul {
    int soulAmount;
    PickUpSoulsAction pickUpSoulsAction;

    /***
     * Constructor.
     *  @param
     *
     */
    public TokenOfSouls(Location location, Soul soul) {
        super("Token Of Souls", '$');
        soul.transferSouls(this);
        this.pickUpSoulsAction = new PickUpSoulsAction(this, location);
        super.addAction(this.pickUpSoulsAction);
    }

    /**
     * Adds token to the player's prev location
     *
     *
     * @param location of the player's previous location
     */
    public void addToken(Location location){
        location.addItem(this);
    }


    @Override
    public boolean addSouls(int souls) {
        soulAmount += souls;
        return true;
    }

    @Override
    public int getSouls() {
        return soulAmount;
    }


    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(soulAmount);}

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        List<Item> listOfSouls = currentLocation.getItems();
        for (Item soul:listOfSouls){
            // If the token of soul is from a chest...
            if (soul.hasCapability(Status.TREASURED_SOULS) && soul == this){
                // Remove pick up action and allow pick up souls action.
                this.allowableActions.clear();
                this.portable = false;
                this.allowableActions.add(this.pickUpSoulsAction);
            }
        }
    }
}
