package game;
import edu.monash.fit2099.engine.*;
import java.util.Random;


/**
 *
 * Cemetery where Undead spawns in
 */
public class Cemetery extends Ground {


    /**
     * Constructor for Cemetery
     *
     *
     */
    public Cemetery() {
        super('c');

    }

    /**
     *
     * @param actor the Actor to check
     * @return false or actor cannot enter.
     */
    @Override
    public boolean canActorEnter(Actor actor){
        return false;
    }


    /**
     * On every passing turn for the Cemetery,have it go through a probability function.If 25%,spawn an Undead
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        Random random = new Random();
        if (!location.containsAnActor()){
            if (random.nextInt(4)+1 == 1){
                spawnUndead(location);} }
    }


    /**
     *
     * Spawns an Undead and places it on the location of the cemetery
     *
     *
     * @param cemetery The location of the Cemetery
     */
    public void spawnUndead(Location cemetery){
        Undead undead = new Undead("Undead");
        Random rand = new Random();
        if (rand.nextInt(2) == 1){
            Broadsword broadsword = new Broadsword("Broadsword",'b',30,"slashes",80);
            undead.addItemToInventory(broadsword);
       }
        else{
            GiantAxe giantAxe = new GiantAxe("GiantAxe", 'g', 50, "slashes", 80);

            undead.addItemToInventory(giantAxe);
        }
        cemetery.addActor(undead);


    }


}
