package game.enums;

/**
 * Enum that represents an ability of Actor, Item, or Ground.
 */
public enum Abilities {
    EMBERFORM,// Ability used by the Lord Of Cinder when it reaches less than half of its health
    RESURRECT, // Skeleton can resurrect if reaches 50%
    TRADING, // Ability used by a Vendor to trade souls for the player's (i.e. The Unkindled) attribute upgrades and
    // items.
    CRITICAL_STRIKE, // Ability used for weapons that has a chance to critically hurt a target.
    TRADABLE_CINDER_OF_YHORM, // Ability used by a Vendor to trade a weapon that was previous used by Yhorm The Giant.
    TRADABLE_CINDER_OF_ALDRICH, // Ability used by a Vendor to trade a weapon that was previous used by Aldrich The Devourer.
    RANGED_WEAPON,
    KICK_THE_CRAP_OUT_OF_YOU
}
