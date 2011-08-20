// Package Declaration
package me.iffa.styxspace;

// Java imports
import java.util.logging.Logger;

// StyxSpace imports
import me.iffa.styxspace.utils.SpaceConfig;
import me.iffa.styxspace.utils.SpacePlanetConfig;
import me.iffa.styxspace.utils.SpaceChunkGenerator;
import me.iffa.styxspace.planets.PlanetsChunkGenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of StyxSpace
 * 
 * @author iffa
 * 
 */
public class StyxSpace extends JavaPlugin {
    // Variables
	public static String prefix = "[StyxSpaceMV]";
	public static String version = "1.1-MV";
	public static final Logger log = Logger.getLogger("Minecraft");
	
    SpaceConfig cMgr = new SpaceConfig();
    SpacePlanetConfig cplaMgr = new SpacePlanetConfig();
  
    /**
     * Called when the plugin is loaded
     */
    @Override
    public void onDisable() {
        log.info(prefix + " Disabled version " + version);
    }

    /**
     * Called when the plugin is loaded
     */
    @Override
    public void onEnable() {
        cMgr.loadConfig();
        cplaMgr.loadConfig();
    }
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    	if (!SpaceConfig.myConfig.getBoolean(
                "worlds." + worldName + ".generation.generateplanets", true)) {
            return new SpaceChunkGenerator();
        } else {
           return new PlanetsChunkGenerator(SpacePlanetConfig.myConfig, this);
        }
    }
}