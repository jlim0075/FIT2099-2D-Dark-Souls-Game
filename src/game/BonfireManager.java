package game;

import edu.monash.fit2099.engine.Location;
import game.enums.Status;

import java.util.HashMap;
import java.util.Objects;

/**
 * A class which manages all the Bonfires and keep track of which Bonfire is the last interacted one.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #getRestablelist()
 *  @see #appendRestableInstance(String, Location)
 *  @see #setLastInteractedBonfire(String)
 *  @see #getLastInteractedBonfire()
 */
public class BonfireManager {
    /**
     * A list of resettable instances (any classes that implements Restable,
     * such as Bonfire implements Restable will be stored in here)
     */
    private static HashMap<String, Location> restableList = new HashMap<String, Location>();

    /**
     * Get the hashmap containing the Name of the Bonfires and their respective locations.
     *
     * @return restableList A hashmap of the Name of the Bonfires and their respective locations.
     */
    public static HashMap<String, Location> getRestablelist(){
        return restableList;
    }

    /**
     * Add the Restable instance to the hashmap.
     *
     * @param bonfireName the interface instance name
     */
    public static void appendRestableInstance(String bonfireName, Location location){
        restableList.put(bonfireName, location);
    }

    /**
     * Remove the status for being last interacted for any of the Bonfires and set the current Bonfire to be
     * last interacted.
     *
     * @param bonfireName the interface instance name
     */
    public static void setLastInteractedBonfire(String bonfireName){
        for (String i : BonfireManager.getRestablelist().keySet()) {
            if (BonfireManager.getRestablelist().get(i).getGround().hasCapability(Status.INTERACTED) && !Objects.equals(i, bonfireName)){
                BonfireManager.getRestablelist().get(i).getGround().removeCapability(Status.INTERACTED);
            }
        }
        BonfireManager.getRestablelist().get(bonfireName).getGround().addCapability(Status.INTERACTED);
    }

    /**
     * Get the location of the last interacted Bonfire.
     *
     * @return  Location of the last interacted Bonfire.
     */
    public static Location getLastInteractedBonfire(){
        for (String i : BonfireManager.getRestablelist().keySet()) {
            if (BonfireManager.getRestablelist().get(i).getGround().hasCapability(Status.INTERACTED)){
                return BonfireManager.getRestablelist().get(i);
            }
        }
        return null;
    }
}
