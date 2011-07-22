package me.iffa.styxspace.utils;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Own Imports
import me.iffa.styxspace.StyxSpace;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

/**
 * A class that handles the configuration file.
 * 
 * @author iffa
 * 
 */
public class SpaceConfig {
	private static final Map<String, Object> CONFIG_DEFAULTS = new HashMap<String, Object>();
	public static Configuration myConfig;

	public static String WORLD_NAME;
	public static Boolean ALWAYS_NIGHT;
	public static Boolean WEATHER;
	public static Boolean HOSTILE_MOBS;
	public static Boolean NEUTRAL_MOBS;
	public static Boolean GIVEHELMET;
	public static Integer ROOM_HEIGHT;
	public static Integer SPACEHELMET;
	public static Boolean HELMET_REQUIRED;
	public static String DEFAULT_ARMOR;
	public static Boolean SUIT_REQUIRED;
	public static Boolean GIVESUIT;
	public static Boolean FLYING;
	static {
		// General
		CONFIG_DEFAULTS.put("styxspace.worldname", "space");
		CONFIG_DEFAULTS.put("styxspace.alwaysnight", true);
		CONFIG_DEFAULTS.put("styxspace.weather", false);
		CONFIG_DEFAULTS.put("styxspace.hostilemobs", false);
		CONFIG_DEFAULTS.put("styxspace.neutralmobs", true);
		CONFIG_DEFAULTS.put("styxspace.breathingarea.maxroomheight", 5);
		CONFIG_DEFAULTS.put("styxspace.helmet.givehelmet", false);
		CONFIG_DEFAULTS.put("styxspace.helmet.blockid", 86);
		CONFIG_DEFAULTS.put("styxspace.helmet.required", false);
		CONFIG_DEFAULTS.put("styxspace.suit.required", false);
		CONFIG_DEFAULTS.put("styxspace.suit.givesuit", false);
		CONFIG_DEFAULTS.put("styxspace.suit.armortype", "iron");
		CONFIG_DEFAULTS.put("styxspace.enablegravity", true);
		// Planetoids
		CONFIG_DEFAULTS.put("styxspace.planets.generateplanets", true);
		CONFIG_DEFAULTS.put("styxspace.planets.seed", -1);
		CONFIG_DEFAULTS.put("styxspace.planets.density", 1000);
		CONFIG_DEFAULTS.put("styxspace.planets.minSize", 10);
		CONFIG_DEFAULTS.put("styxspace.planets.maxSize", 40);
		CONFIG_DEFAULTS.put("styxspace.planets.minDistance", 100);
		CONFIG_DEFAULTS.put("styxspace.planets.minShellSize", 10);
		CONFIG_DEFAULTS.put("styxspace.planets.maxShellSize", 20);
		CONFIG_DEFAULTS.put("styxspace.planets.floorBlock", "STATIONARY_WATER");
		CONFIG_DEFAULTS.put("styxspace.planets.floorHeight", 0);
		// Planetoids shells & cores
		CONFIG_DEFAULTS
				.put("styxspace.planets.bedrock", Boolean.valueOf(false));
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
		CONFIG_DEFAULTS.put("styxspace.planets.blocks.cores", cores);
		CONFIG_DEFAULTS.put("styxspace.planets.blocks.shells", shells);
	}

	/**
	 * Initializes the YAML file
	 * 
	 * @author Pandarr
	 */
	public void loadConfig() {
		File configFile = new File(Bukkit.getServer().getPluginManager()
				.getPlugin("StyxSpace").getDataFolder(), "config.yml");
		if (configFile.exists()) {
			myConfig = new Configuration(configFile);
			myConfig.load();
			myConfig.setHeader("# StyxSpace Configuration\r\n# Please see http://bit.ly/lu0VuE for configuration details.");
			for (String prop : CONFIG_DEFAULTS.keySet()) {
				if (myConfig.getProperty(prop) == null) {
					myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
					StyxSpace.log
							.info(StyxSpace.prefix
									+ " If you see this message, please delete your configuration file for it to be updated! Thanks");
				}
			}
			WORLD_NAME = (String) myConfig.getProperty("styxspace.worldname");
			ALWAYS_NIGHT = myConfig.getBoolean("styxspace.alwaysnight", true);
			WEATHER = myConfig.getBoolean("styxspace.weather", false);
			HOSTILE_MOBS = myConfig.getBoolean("styxspace.hostilemobs", false);
			NEUTRAL_MOBS = myConfig.getBoolean("styxspace.neutralmobs", true);
			GIVEHELMET = myConfig.getBoolean("styxspace.helmet.givehelmet",
					false);
			SPACEHELMET = myConfig.getInt("styxspace.helmet.blockid", 86);
			HELMET_REQUIRED = myConfig.getBoolean("styxspace.helmet.required",
					false);
			GIVESUIT = myConfig.getBoolean("styxspace.suit.givesuit", false);
			DEFAULT_ARMOR = myConfig.getString("styxspace.suit.armortype",
					"iron");
			SUIT_REQUIRED = myConfig.getBoolean("styxspace.suit.required",
					false);
			ROOM_HEIGHT = myConfig.getInt(
					"styxspace.breathingarea.maxroomheight", 5);
			FLYING = myConfig.getBoolean("styxspace.enablegravity", true);
		} else {
			try {
				Bukkit.getServer().getPluginManager().getPlugin("StyxSpace")
						.getDataFolder().mkdir();
				configFile.createNewFile();
				myConfig = new Configuration(configFile);
				myConfig.setHeader("# StyxSpace Configuration\r\n# Please see http://bit.ly/lu0VuE for configuration details.");
				for (String prop : CONFIG_DEFAULTS.keySet()) {
					myConfig.setProperty(prop, CONFIG_DEFAULTS.get(prop));
				}
				if ((long) myConfig.getDouble("styxspace.planets.seed", -1.0) == -1) {
					myConfig.setProperty("styxspace.planets.seed", Bukkit
							.getServer().getWorlds().get(0).getSeed());
				}
				myConfig.save();
				WORLD_NAME = (String) myConfig
						.getProperty("styxspace.worldname");
				ALWAYS_NIGHT = myConfig.getBoolean("styxspace.alwaysnight",
						true);
				WEATHER = myConfig.getBoolean("styxspace.weather", false);
				HOSTILE_MOBS = myConfig.getBoolean("styxspace.hostilemobs",
						false);
				NEUTRAL_MOBS = myConfig.getBoolean("styxspace.neutralmobs",
						true);
				GIVEHELMET = myConfig.getBoolean("styxspace.helmet.givehelmet",
						false);
				SPACEHELMET = myConfig.getInt("styxspace.helmet.blockid", 86);
				GIVESUIT = myConfig
						.getBoolean("styxspace.suit.givesuit", false);
				DEFAULT_ARMOR = myConfig.getString("styxspace.suit.armortype",
						"iron");
				SUIT_REQUIRED = myConfig.getBoolean("styxspace.suit.required",
						false);
				HELMET_REQUIRED = myConfig.getBoolean(
						"styxspace.helmet.required", false);
				ROOM_HEIGHT = myConfig.getInt(
						"styxspace.breathingarea.maxroomheight", 5);
				ROOM_HEIGHT = myConfig.getInt(
						"styxspace.breathingarea.maxroomheight", 5);
				FLYING = myConfig.getBoolean("styxspace.enablegravity", true);
				StyxSpace.log.info(StyxSpace.prefix
						+ " Generated configuration file for version "
						+ StyxSpace.version);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}
