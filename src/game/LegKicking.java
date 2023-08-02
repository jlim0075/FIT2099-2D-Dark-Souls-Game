package game;

import edu.monash.fit2099.engine.*;

public class LegKicking extends WeaponItem {
    public LegKicking() {
        super("nothing", '/', 55, "kicks", 100);
        super.portable = false;
    }
}