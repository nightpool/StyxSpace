// Package Declaration
package me.iffa.styxspace;

// Java imports
import java.util.logging.Logger;

// StyxSpace imports
import me.iffa.styxspace.listeners.SpaceEntityListener;
import me.iffa.styxspace.listeners.SpacePlayerListener;
import me.iffa.styxspace.listeners.SpacePortalBListener;
import me.iffa.styxspace.listeners.SpacePortalListener;
import me.iffa.styxspace.listeners.SpaceWeatherListener;
import me.iffa.styxspace.utils.SpaceCommandExec;
import me.iffa.styxspace.utils.SpaceConfig;
import me.iffa.styxspace.utils.SpacePortalConfig;
import me.iffa.styxspace.api.SpaceWorldHandler;
import me.iffa.styxspace.api.SpaceConfigHandler;
import me.iffa.styxspace.utils.SpacePlanetConfig;

// Bukkit imports
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

//Permissions (Nijiko)
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Main class of StyxSpace
 * 
 * @author iffa
 * 
 */
public class StyxSpace extends JavaPlugin {
    // Variables

    SpaceConfig cMgr = new SpaceConfig();
    SpacePlanetConfig cplaMgr = new SpacePlanetConfig();
    SpacePortalConfig cpMgr = new SpacePortalConfig();
    public static String prefix = "[StyxSpace]";
    public static String version = "1.1 [TheOne]";
    public static final Logger log = Logger.getLogger("Minecraft");
    public static World space = null;
    public static BukkitScheduler scheduler;
    public static PermissionHandler permissionHandler;
    private SpaceCommandExec sce = null;
    public static SpaceWorldHandler worldHandler;
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
        cplaMgr.loadConfig();
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

        // Creating all space worlds.
        worldHandler = new SpaceWorldHandler(this);
        worldHandler.createSpaceWorlds();

        // Initializing the CommandExecutor
        sce = new SpaceCommandExec(this);
        getCommand("space").setExecutor(sce);

        // Checking if it should always be night in space
        scheduler = getServer().getScheduler();
        for (World world : worldHandler.getSpaceWorlds()) {
            if (SpaceConfigHandler.forceNight(world)) {
                worldHandler.startForceNightTask(world);
            }
        }

        // Setting up Permissions (Nijiko)
        setupPermissions();
        log.info(prefix + " Enabled version " + version);
    }

    /**
     * Sets up Permissions (Nijiko)
     */
    private void setupPermissions() {
        if (permissionHandler != null) {
            return;
        }
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (permissionsPlugin == null) {
            log.info(prefix
                    + " Permission system not detected, defaulting to OP");
            return;
        }
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
        log.info(prefix
                + " Found and will use plugin "
                + ((Permissions) permissionsPlugin).getDescription().getFullName());
    }
}
