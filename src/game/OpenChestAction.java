package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;

import java.util.Random;

/**
 * Class representing the opening chest action.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 * @see OpenChestAction(Chest, Location)
 * @see #execute(Actor, GameMap)
 * @see #menuDescription(Actor)
 */
public class OpenChestAction extends Action{
    private Chest chestBeingOpened;
    private Location chestLocation;


    private final int chanceOfTransforming = 50;

    public OpenChestAction(Chest chestBeingOpened, Location chestLocation) {
        this.chestBeingOpened = chestBeingOpened;
        this.chestLocation = chestLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        this.chestLocation.setGround(new Dirt());
        Random rand = new Random();
        // Checks if the chest transforms after interacted...
        if (!(rand.nextInt(100) <= this.chanceOfTransforming)) {
            TokenOfSouls token = this.chestBeingOpened.getTokenOfSouls();
            // Differentiate the souls from dropped souls.
            token.addCapability(Status.TREASURED_SOULS);
            token.addToken(this.chestLocation);
            for (int i = 0; i < 2; i++){
                if (rand.nextBoolean()) {
                    token.addToken(this.chestLocation);
                }
            }
            return "The opened chest was revealed to be a box of souls!";
        }
        else{
            this.chestLocation.addActor(new Mimic(this.chestLocation, this.chestBeingOpened));
            return "Oh crap. The chest is a mimic!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " opens a chest";
    }
}
