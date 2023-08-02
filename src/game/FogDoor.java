package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;
import game.enums.Status;

abstract class FogDoor extends Ground {
    public FogDoor() {
        super('=');
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }
}