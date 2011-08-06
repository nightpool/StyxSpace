// Package Declaration
package me.iffa.styxspace.utils;

// Java Imports
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//StyxSpace Import
import me.iffa.styxspace.StyxSpace;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

/**
 * A class that handles the planet generation configuration file.
 * 
 * @author iffa
 * @author Pandarr
 */
public class SpacePlanetConfig {
    // Variables
    private static final Map<String, Object> CONFIG_DEFAULTS = new HashMap<String, Object>();
    public static Configuration myConfig;

    static {
        // Planetoids
        CONFIG_DEFAULTS.put("seed", -1);
        CONFIG_DEFAULTS.put("density", 1000);
        CONFIG_DEFAULTS.put("minSize", 10);
        CONFIG_DEFAULTS.put("maxSize", 40);
        CONFIG_DEFAULTS.put("minDistance", 100);
        CONFIG_DEFAULTS.put("minShellSize", 10);
        CONFIG_DEFAULTS.put("maxShellSize", 20);
        CONFIG_DEFAULTS.put("floorBlock", "STATIONARY_WATER");
        CONFIG_DEFAULTS.put("floorHeight", 0);
        // Planetoids shells & cores
        CONFIG_DEFAULTS.put("bedrock", Boolean.valueOf(false));
        ArrayList<String> cores = new ArrayList<String>();
        ArrayList<String> shells = new ArrayList<String>();
        shells.add(Material.STONE.toString() + "-1.0");
        shells.add(Material.DIRT.toString() + "-1.0");
        shells.add(Material.LEAVES.toString() + "-0.9");
        shells.add(Material.ICE.toString() + "-0.9");
        shells.add(Material.SNOW_BLOCK.toString() + "-0.9");
        shells.add(Material.GLOWSTONE.toString() + "-0.4");
        shells.add(Material.BRICK.toString() + "-0.6");
        shells.add(Material.SANDSTONE.toString() + "-0.8");
        shells.add(Material.OBSIDIAN.toString() + "-0.5");
        shells.add(Material.MOSSY_COBBLESTONE.toString() + "-0.3");
        shells.add(Material.WOOL.toString() + "-0.4");
        shells.add(Material.GLASS.toString() + "-0.9");
        cores.add(Material.PUMPKIN.toString() + "-0.8");
        cores.add(Material.STATIONARY_LAVA.toString() + "-0.8");
        cores.add(Material.STATIONARY_WATER.toString() + "-1.0");
        cores.add(Material.COAL_ORE.toString() + "-1.0");
        cores.add(Material.IRON_ORE.toString() + "-0.8");
        cores.add(Material.DIAMOND_ORE.toString() + "-0.4");
        cores.add(Material.CLAY.toString() + "-0.3");
        cores.add(Material.LAPIS_ORE.toString() + "-0.4");
        cores.add(Material.LOG.toString() + "-1.0");
        cores.add(Material.GOLD_ORE.toString() + "-0.6");
        cores.add(Material.REDSTONE_ORE.toString() + "-0.75");
        cores.add(Material.SAND.toString() + "-1.0");
        cores.add(Material.BEDROCK.toString() + "-0.5");
        cores.add(Material.AIR.toString() + "-1.0");
        CONFIG_DEFAULTS.put("blocks.cores", cores);
        CONFIG_DEFAULTS.put("blocks.shells", shells);
    }

    /**
     * Initializes the YAML file
     */
    public void loadConfig() {
        File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("StyxSpace").getDataFolder(), "planets.yml");
        if (configFile.exists()) {
            myConfig = new Configuration(configFile);
            myConfig.load();
            myConfig.setHeader("# StyxSpace Planet Generation Configuration\r\n# Please see http://bit.ly/qUbNWw for configuration details.");
            for (String prop : CONFIG_DEFAULTS.keySet()) {
                if (myConfig.getProperty(prop) == null) {
                    myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
                    StyxSpace.log.info(StyxSpace.prefix
                            + " If you see this message, please delete your portal generation file for it to be updated! Thanks");
                }
            }
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("StyxSpace").getDataFolder().mkdir();
                configFile.createNewFile();
                myConfig = new Configuration(configFile);
                myConfig.setHeader("# StyxSpace Planet Generation Configuration\r\n# Please see http://bit.ly/qUbNWwE for configuration details.");
                for (String prop : CONFIG_DEFAULTS.keySet()) {
                    myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
                }
                if ((long) myConfig.getDouble("seed", -1.0) == -1) {
                    myConfig.setProperty("seed", Bukkit.getServer().getWorlds().get(0).getSeed());
                }
                myConfig.save();
                StyxSpace.log.info(StyxSpace.prefix
                        + " Generated planet configuration for version "
                        + StyxSpace.version);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
