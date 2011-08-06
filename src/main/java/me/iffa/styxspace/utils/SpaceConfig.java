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
import org.bukkit.util.config.Configuration;

/**
 * A class that handles the configuration file.
 * 
 * @author iffa
 * @author Pandarr
 */
public class SpaceConfig {
    // Variables
    private static final Map<String, Object> CONFIG_DEFAULTS = new HashMap<String, Object>();
    public static Configuration myConfig;

    static {
        // General
        CONFIG_DEFAULTS.put("styxspace.global.helmet.blockid", 86);
        CONFIG_DEFAULTS.put("styxspace.global.suit.armortype", "iron");
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.alwaysnight", true);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.weather", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.hostilemobs", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.neutralmobs", true);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.breathingarea.maxroomheight", 5);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.helmet.givehelmet", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.helmet.required", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.suit.required", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.suit.givesuit", false);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.generation.glowstonechance", 1);
        CONFIG_DEFAULTS.put("styxspace.worlds.world_space.generation.generateplanets", true);
    }

    /**
     * Initializes the YAML file
     */
    public void loadConfig() {
        File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("StyxSpace").getDataFolder(), "config.yml");
        if (configFile.exists()) {
            myConfig = new Configuration(configFile);
            myConfig.load();
            myConfig.setHeader("# StyxSpace Configuration\r\n# Please see http://bit.ly/lu0VuE for configuration details.");
            for (String prop : CONFIG_DEFAULTS.keySet()) {
                if (myConfig.getProperty(prop) == null) {
                    myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
                    StyxSpace.log.info(StyxSpace.prefix
                            + " If you see this message, please delete your configuration file for it to be updated! Thanks");
                }
            }
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("StyxSpace").getDataFolder().mkdir();
                configFile.createNewFile();
                myConfig = new Configuration(configFile);
                myConfig.setHeader("# StyxSpace Configuration\r\n# Please see http://bit.ly/lu0VuE for configuration details.");
                for (String prop : CONFIG_DEFAULTS.keySet()) {
                    myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
                }
                myConfig.save();
                StyxSpace.log.info(StyxSpace.prefix
                        + " Generated configuration file for version "
                        + StyxSpace.version);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
