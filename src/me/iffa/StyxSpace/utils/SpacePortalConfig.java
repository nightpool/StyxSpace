package me.iffa.styxspace.utils;

// Java Imports
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

/**
 * A class that handles the sign portal file.
 * 
 * @author iffa
 * 
 */
public class SpacePortalConfig {
	private static final Map<String, Object> CONFIG_DEFAULTS = new HashMap<String, Object>();
	public static Configuration config;
	static {
		CONFIG_DEFAULTS.put("StyxSpacePortals", null);
	}

	/**
	 * Initializes the YAML file
	 * 
	 * @author Pandarr
	 */
	public void loadConfig() {
		File configFile = new File(Bukkit.getServer().getPluginManager()
				.getPlugin("StyxSpace").getDataFolder(), "portals.yml");
		if (configFile.exists()) {
			config = new Configuration(configFile);
			config.load();
			config.setHeader("# StyxSpace portal file. Please do not edit unless you know what you're doing!");
			for (String prop : CONFIG_DEFAULTS.keySet()) {
				if (config.getProperty(prop) == null) {
					config.setProperty(prop, CONFIG_DEFAULTS.get(prop));
				}
			}
		} else {
			try {
				Bukkit.getServer().getPluginManager().getPlugin("StyxSpace")
						.getDataFolder().mkdir();
				configFile.createNewFile();
				config = new Configuration(configFile);
				config.setHeader("# StyxSpace portal file. Please do not edit unless you know what you're doing!");
				for (String prop : CONFIG_DEFAULTS.keySet()) {
					config.setProperty(prop, CONFIG_DEFAULTS.get(prop));
				}
				config.save();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}
