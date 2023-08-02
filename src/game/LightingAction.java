package game;

import edu.monash.fit2099.engine.*;

/**
 * Special action class for players (i.e. The Unkindled) to lights up Bonfires.
 *  @author Hoh Zheng Jie
 *  @version 1.1.0
 *  @see #LightingAction(Ground)
 *  @see #execute(Actor, GameMap)
 *  @see #menuDescription(Actor)
 */
public class LightingAction extends Action {
    private Ground littableBonfire; // Weapons that are tradable.

    /**
     * Constructor
     * @param bonfire The bonfire to be lit.
     */
    public LightingAction(Ground bonfire) {
        this.littableBonfire = bonfire;
    }

    /**
     * Allow the Bonfire which the player is interacting with to be lit up.
     * @param actor The player trading.
     * @param map The map of the current actor's location.
     * @return a String saying the Bonfire is lit.
     */
    public String execute(Actor actor, GameMap map) {
        Bonfire littableBonfire = (Bonfire) this.littableBonfire;
        littableBonfire.lit();
        return """
                 .########...#######..##....##.########.####.########..########....##.......####.########
                .##.....##.##.....##.###...##.##........##..##.....##.##..........##........##.....##...
                .##.....##.##.....##.####..##.##........##..##.....##.##..........##........##.....##...
                .########..##.....##.##.##.##.######....##..########..######......##........##.....##...
                .##.....##.##.....##.##..####.##........##..##...##...##..........##........##.....##...
                .##.....##.##.....##.##...###.##........##..##....##..##..........##........##.....##...
                .########...#######..##....##.##.......####.##.....##.########....########.####....##...""";
    }

    /**
     * Provides a proper action description for the players who want to light a Bonfire.
     * @param actor The player lighting up a Bonfire.
     * @return a String describing the lighting action can be executed for the menu display.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " lights the bonfire";
    }
}
