package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Soul;


/**
 * Class representing the Chest.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see Chest
 * @see #tick(Location)
 * @see #allowableActions(Actor, Location, String)
 * @see #transferSouls(Soul)
 * @see #getTokenOfSouls()
 * @see #getSouls()
 */
public class Chest extends Ground implements Soul {
    private OpenChestAction openChestAction;
    private Location chestLocation;
    private TokenOfSouls treasuredSouls;
    private int souls;
    public Chest() {
        super('?');
        this.souls = 100;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        this.chestLocation = location;
        this.openChestAction = new OpenChestAction(this, chestLocation);
        this.treasuredSouls = new TokenOfSouls(this.chestLocation,this.asSoul());
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction){
        if (actor.hasCapability(Status.MINDFUL_ENTITY)) {
            return new Actions(openChestAction);
        }
        return new Actions();
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(this.souls);
    }

    @Override
    public int getSouls() {
        return this.souls;
    }

    public TokenOfSouls getTokenOfSouls() {
        return this.treasuredSouls;
    }
}
