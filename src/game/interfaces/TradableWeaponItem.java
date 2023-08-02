package game.interfaces;

/**
 * Interface for weapon items which are tradable.
 *
 * This interface thus far provides a method that returns the cost or soul required to acquire a tradable weapon.
 */
public interface TradableWeaponItem {
    /**
     * The cost (souls required) of the tradable weapon.
     *
     * @return the cost, in souls
     */
    int getCost();

    /**
     * The name of the tradable weapon.
     *
     * @return the name, in string.
     */
    String getName();
}
