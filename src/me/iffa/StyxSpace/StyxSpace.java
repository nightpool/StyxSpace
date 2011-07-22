package me.iffa.styxspace;

// Java imports
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
// StyxSpace imports
import me.iffa.styxspace.api.*;
import me.iffa.styxspace.listeners.SpaceEntityListener;
import me.iffa.styxspace.listeners.SpacePlayerListener;
import me.iffa.styxspace.listeners.SpacePortalBListener;
import me.iffa.styxspace.listeners.SpacePortalListener;
import me.iffa.styxspace.listeners.SpaceWeatherListener;
import me.iffa.styxspace.planets.PlanetsChunkGenerator;
import me.iffa.styxspace.schedulers.SpaceRunnable;
import me.iffa.styxspace.utils.SpaceChunkGenerator;
import me.iffa.styxspace.utils.SpaceCommandExec;
import me.iffa.styxspace.utils.SpaceConfig;
import me.iffa.styxspace.utils.SpacePortalConfig;
// Bukkit imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
// Permissions
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Main class of StyxSpace
 * 
 * @author iffa
 * 
 */
public class StyxSpace extends JavaPlugin {
	// Configuration file stuff
	SpaceConfig cMgr = new SpaceConfig();
	SpacePortalConfig cpMgr = new SpacePortalConfig();

	// Variables
	public static String prefix = "[StyxSpace]";
	public static String version = "0.9 [Netherrack]";
	public static Logger log = Logger.getLogger("Minecraft");
	public static World space = null;
	public static BukkitScheduler scheduler;
	public static PermissionHandler permissionHandler;
	private SpaceCommandExec sce = null;

	// WeatherListener
	private final SpaceWeatherListener weatherListener = new SpaceWeatherListener(
			this);
	// EntityListener
	private final SpaceEntityListener entityListener = new SpaceEntityListener(
			this);
	// PlayerListener (general)
	private final SpacePlayerListener playerListener = new SpacePlayerListener(
			this);
	// PlayerListener (portals)
	private final SpacePortalListener pplayerListener = new SpacePortalListener(
			this);
	// BlockListener (portals)
	private final SpacePortalBListener bListener = new SpacePortalBListener(
			this);

	// Plugins using SpaceListener
	public static Set<SpaceListener> listeners = new HashSet<SpaceListener>();

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
		cpMgr.loadConfig();
		PluginManager pm = getServer().getPluginManager();

		// Registering other events
		pm.registerEvent(Event.Type.WEATHER_CHANGE, weatherListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener,
				Event.Priority.Normal, this);

		// Registering entity & player events
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pplayerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,
				Event.Priority.Normal, this);

		// Registering block events
		pm.registerEvent(Event.Type.SIGN_CHANGE, bListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, bListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PHYSICS, bListener,
				Event.Priority.Normal, this);

		// Choosing which chunk generator to use
		if (!SpaceConfig.myConfig.getBoolean(
				"styxspace.planets.generateplanets", true)) {
			// Using planet chunk generator
			Bukkit.getServer().createWorld(SpaceConfig.WORLD_NAME,
					World.Environment.NORMAL, new SpaceChunkGenerator());
		} else {
			// Using normal space chunk generator
			Bukkit.getServer().createWorld(SpaceConfig.WORLD_NAME,
					World.Environment.NORMAL,
					new PlanetsChunkGenerator(SpaceConfig.myConfig, this));
		}

		// Initializing the CommandExecutor
		sce = new SpaceCommandExec(this);
		getCommand("space").setExecutor(sce);

		// Checking if it should always be night in space
		scheduler = getServer().getScheduler();
		SpaceRunnable task = new SpaceRunnable();
		if (SpaceConfig.ALWAYS_NIGHT == true) {
			scheduler.scheduleSyncRepeatingTask(this, task, 60, 8399);
		}

		// Setting up Permissions (Nijikok.)
		setupPermissions();
		log.info(prefix + " Enabled version " + version);
	}

	/**
	 * Gets the space world of the server
	 * 
	 * @return The space world
	 */
	public static World getSpace() {
		if (space == null) {
			space = Bukkit.getServer().getWorld(SpaceConfig.WORLD_NAME);
		}
		return space;
	}

	/**
	 * Sets up Permissions (Nijikok.)
	 */
	private void setupPermissions() {
		if (permissionHandler != null) {
			return;
		}

		Plugin permissionsPlugin = this.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (permissionsPlugin == null) {
			log.info(prefix
					+ " Permission system not detected, defaulting to OP");
			return;
		}

		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		log.info(prefix
				+ " Found and will use plugin "
				+ ((Permissions) permissionsPlugin).getDescription()
						.getFullName());
	}

	/**
	 * Called when a player uses 'spacev'
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spacev") && args.length == 1
				&& args[0].equalsIgnoreCase("iffa")) {
			sender.sendMessage(ChatColor.DARK_PURPLE + prefix + " Version: "
					+ version);
			sender.sendMessage(ChatColor.DARK_PURPLE
					+ "Author: iffa (iffamies1337@hotmail.fi)");
			sender.sendMessage(ChatColor.DARK_PURPLE + "(c) Styx Reborn 2011");
			return true;
		}
		return true;
	}
}
