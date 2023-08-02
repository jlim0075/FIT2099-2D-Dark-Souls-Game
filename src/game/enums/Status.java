package game.enums;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_ENEMY, // Use this capability to be hostile towards something (e.g., to be attacked by enemy)
    MINDFUL_ENTITY, // Use this status so that an entity can be recognized as a mindful player (i.e. The Unkindled).
    ENEMY_DIE_INSTANTLY, // Used for the Undead as a de-buff (Have a 10% chance to die each turn when not attacked)
    RESTING, // Used when a player is resting at a Bonfire.
    INTERACTED, // Used when a player is resting at a Bonfire, to signify the last resting place.
    DISARMED, // Used when a player is charging his/her Storm Ruler.
    STUNNED, // Used when an enemy is stunned.
    CONSUMABLE, // Used to identify an item can be consumed (specifically an Estus Flask).
    ON_SAFE_ZONE, // Used for players standing on floor tiles so that enemies can't enter.
    SAFE_ZONE, // Used for locations that are safe from enemies (specifically floor tiles).
    LORDS_OF_CINDER, // Used to differentiate between Lords of Cinder and normal enemies.
    TREASURED_SOULS //Used to differentiate between drop souls and souls in a chest/mimic.
}
