package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.enums.Status;

import java.util.HashMap;

/**
 * A class which manages all the Bonfires and keep track of which Bonfire is the last interacted one.
 *  @author Hoh Zheng Jie
 *  @version 1.0.0
 *  @see #getMaplist()
 *  @see #getMap(String)
 */
public class MapManager {
    /**
     * A list of resettable instances (any classes that implements Restable,
     * such as Bonfire implements Restable will be stored in here)
     */
    private static HashMap<String, GameMap> mapList = new HashMap<String, GameMap>();

    /**
     * Get the hashmap containing the Name of the Bonfires and their respective locations.
     *
     * @return restableList A hashmap of the Name of the Bonfires and their respective locations.
     */
    public static HashMap<String, GameMap> getMaplist(){
        return mapList;
    }

    /**
     * Add the GameMap instance to the hashmap.
     *
     * @param mapName The name of the map.
     * @param map The appended Game map instance.
     */
    public static void appendMap(String mapName, GameMap map){
        mapList.put(mapName, map);
    }

    public static GameMap getMap(String mapName){
        for (String i : MapManager.getMaplist().keySet()) {
            if (i == mapName){
                return MapManager.getMaplist().get(i);
            }
        }
        return null;
    }
}