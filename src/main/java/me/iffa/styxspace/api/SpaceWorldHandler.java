// Package Declaration
package me.iffa.styxspace.api;

// Java Imports
import java.util.ArrayList;
import java.util.List;

// StyxSpace Imports
import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.planets.PlanetsChunkGenerator;
import me.iffa.styxspace.schedulers.SpaceRunnable;
import me.iffa.styxspace.utils.SpaceChunkGenerator;
import me.iffa.styxspace.utils.SpaceConfig;
import me.iffa.styxspace.utils.SpacePlanetConfig;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Class that handles space worlds. (internally)
 * 
 * @author iffa
 */
public class SpaceWorldHandler {
    // Variables
    private StyxSpace plugin;
    public static List<World> spaceWorlds = new ArrayList<World>();

    // Constructor
    public SpaceWorldHandler(StyxSpace instance) {
        plugin = instance;
    }

    /**
     * Creates all space worlds from the configuration file. WARNING: Do not use this with your plugin, as it may cause explosions etc etc!
     */
    public void createSpaceWorlds() {
        List<String> worlds = SpaceConfig.myConfig.getKeys("styxspace.worlds");
        if (worlds == null) {
            StyxSpace.log.severe(StyxSpace.prefix + " Your configuration file has no worlds! Cancelling world generation process.");
            return;
        }
        for (String world : worlds) {
            if (plugin.getServer().getWorld(world) == null) {
                // Choosing which chunk generator to use
                if (!SpaceConfig.myConfig.getBoolean(
                        "styxspace.worlds." + world + ".generation.generateplanets", true)) {
                    plugin.getServer().createWorld(world,
                            World.Environment.NORMAL, new SpaceChunkGenerator());
                    spaceWorlds.add(Bukkit.getServer().getWorld(world));
                } else {
                    plugin.getServer().createWorld(world,
                            World.Environment.NORMAL,
                            new PlanetsChunkGenerator(SpacePlanetConfig.myConfig, plugin));
                    spaceWorlds.add(Bukkit.getServer().getWorld(world));
                }
            }
        }
    }

    /**
     * Starts the force night task if required.
     * 
     * @param task SpaceRunnable task
     */
    public void startForceNightTask(World world) {
        SpaceRunnable task = new SpaceRunnable(world);
        StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task, 60, 8399);
    }

    /**
     * Gives all the space worlds of the server.
     * 
     * @return all space worlds as a List
     */
    public List<World> getSpaceWorlds() {
        return spaceWorlds;
    }

    /**
     * Checks if a world is a space world.
     * 
     * @param world World to check
     * 
     * @return true if the world is a space world
     */
    public boolean isSpaceWorld(World world) {
        if (spaceWorlds.contains(world)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a player is in a space world.
     * 
     * @param player Player to check
     * @param world Space world
     * 
     * @return true if the player is in the specified space world
     */
    public boolean isInSpace(Player player, World world) {
        if (spaceWorlds.contains(world) && player.getWorld() == world) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a player is in any space world.
     * 
     * @return true if the player is in a space world
     */
    public boolean isInAnySpace(Player player) {
        for (World world : spaceWorlds) {
            if (player.getWorld() == world) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the space world a player is in.
     * 
     * @param player Player
     * @return null if not in a space world
     */
    public World getSpaceWorld(Player player) {
        if (getSpaceWorlds().contains(player.getWorld())) {
            return getSpaceWorlds().get(getSpaceWorlds().indexOf(player.getWorld()));
        }
        return null;
    }
}
