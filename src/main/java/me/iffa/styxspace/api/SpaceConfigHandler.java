// Package Declaration
package me.iffa.styxspace.api;

// StyxSpace Imports
import me.iffa.styxspace.utils.SpaceConfig;

// Bukkit Imports
import org.bukkit.World;

/**
 * Static (because I'm too lazy to make an object) methods to use internally to handle world-specific configuration.
 * 
 * @author iffa
 */
public class SpaceConfigHandler {
    /**
     * Gets the world name of a world (why is this even here?)
     * 
     * @param world World
     * 
     * @return world name
     */
    public static String getWorldName(World world) {
        return world.getName();
    }

    /**
     * Gets the required helmet-state of a world.
     * 
     * @param world World
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".helmet.required", true);
    }

    /**
     * Gets the required suit-state of a world.
     * 
     * @param world World
     * 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".suit.required", true);
    }

    /**
     * Gets the helmet given-state of a world.
     * 
     * @return true if a helmet is given when teleporting to this world
     */
    public static boolean isHelmetGiven() {
        return SpaceConfig.myConfig.getBoolean("global.givehelmet", true);
    }

    /**
     * Gets the suit given-state of a world.
     * 
     * @return true if a suit is given when teleporting to this world
     */
    public static boolean isSuitGiven() {
        return SpaceConfig.myConfig.getBoolean("global.givesuit", true);
    }

    /**
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".hostilemobs", false);
    }

    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".neutralmobs", true);
    }

    /**
     * Gets the force night-state of a world.
     * 
     * @param world World
     * 
     * @return true if night is forced
     */
    public static boolean forceNight(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".alwaysnight", true);
    }

    /**
     * Gets the helmet blockid of a world.
     * 
     * @return block id integer
     */
    public static int getHelmetBlock() {
        return SpaceConfig.myConfig.getInt("global.blockid", 86);
    }

    /**
     * Gets the suit armortype of a world.
     * 
     * @return armortype string
     */
    public static String getArmorType() {
        return SpaceConfig.myConfig.getString("global.armortype", "iron");
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param world World
     * 
     * @return room height int
     */
    public static int getRoomHeight(World world) {
        return SpaceConfig.myConfig.getInt("worlds." + world.getName() + ".breathingarea.maxroomheight", 5);
    }

    /**
     * Gets the weather allowed-state of a world.
     * 
     * @param world World
     * 
     * @return true if weather is allowed
     */
    public static boolean allowWeather(World world) {
        return SpaceConfig.myConfig.getBoolean("worlds." + world.getName() + ".weather", false);
    }

    /**
     * Gets the glowstone chance of a world.
     * 
     * @param world World
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(World world) {
        return SpaceConfig.myConfig.getInt("worlds." + world.getName() + ".generation.glowstonechance", 1);
    }
}
