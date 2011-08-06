// Package Declaration
package me.iffa.styxspace.listeners;

// StyxSpace Imports
import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.event.misc.TeleportToSpaceEvent;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.utils.SpacePortalConfig;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * PlayerListener for portal related events
 * 
 * @author iffa
 * 
 */
public class SpacePortalListener extends PlayerListener {
    // Variables
    public SpacePlayer spacePlayer = new SpacePlayer();
    private StyxSpace plugin;

    // Constructor
    public SpacePortalListener(StyxSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player interacts with something.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null
                || event.getClickedBlock().getType() == Material.AIR) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block.getType() == Material.SIGN_POST
                || block.getType() == Material.WALL_SIGN) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase("[StyxSpace]")
                        && sign.getLine(1) != null && sign.getLine(2) != null) {
                    if (spacePlayer.hasPermission("StyxSpace.portal.use",
                            player)) {
                        if (SpacePortalConfig.config.getProperty("StyxSpacePortals."
                                + sign.getLine(2) + ".world") == null
                                || SpacePortalConfig.config.getProperty("StyxSpacePortals."
                                + sign.getLine(2) + ".x") == null
                                || SpacePortalConfig.config.getProperty("StyxSpacePortals."
                                + sign.getLine(2) + ".y") == null
                                || SpacePortalConfig.config.getProperty("StyxSpacePortals."
                                + sign.getLine(2) + ".z") == null) {
                            event.getPlayer().sendMessage(
                                    ChatColor.RED
                                    + StyxSpace.prefix
                                    + " The destination doesn't exist!");
                            return;
                        } else {
                            Location tpDest = new Location(
                                    Bukkit.getServer().getWorld(
                                    SpacePortalConfig.config.getString(
                                    "StyxSpacePortals."
                                    + sign.getLine(2).trim()
                                    + ".world", Bukkit.getServer().getWorlds().get(0).getName())),
                                    SpacePortalConfig.config.getDouble(
                                    "StyxSpacePortals."
                                    + sign.getLine(2).trim()
                                    + ".x", Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getX()),
                                    SpacePortalConfig.config.getDouble(
                                    "StyxSpacePortals."
                                    + sign.getLine(2).trim()
                                    + ".y", Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getY()),
                                    SpacePortalConfig.config.getDouble(
                                    "StyxSpacePortals."
                                    + sign.getLine(2).trim()
                                    + ".z", Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getZ()));
                            if (tpDest.getWorld() != event.getPlayer().getWorld()) {
                                if (!StyxSpace.worldHandler.getSpaceWorlds().contains(event.getPlayer().getWorld())
                                        && StyxSpace.worldHandler.getSpaceWorlds().contains(tpDest.getWorld())) {
                                    // Notify listeners.
                                    TeleportToSpaceEvent e = new TeleportToSpaceEvent(
                                            "TeleportToSpaceEvent",
                                            event.getPlayer(), tpDest);
                                    if (e.isCancelled()) {
                                        event.setCancelled(true);
                                        return;
                                    }
                                    event.getPlayer().teleport(tpDest);
                                } else if (StyxSpace.worldHandler.getSpaceWorlds().contains(event.getPlayer().getWorld())
                                        && StyxSpace.worldHandler.getSpaceWorlds().contains(tpDest.getWorld())) {
                                    // Notify listeners.
                                    TeleportToSpaceEvent e = new TeleportToSpaceEvent(
                                            "TeleportToSpaceEvent",
                                            event.getPlayer(), tpDest);
                                    if (e.isCancelled()) {
                                        event.setCancelled(true);
                                        return;
                                    }
                                    event.getPlayer().teleport(tpDest);
                                    return;
                                }
                            }
                        }
                    } else {
                        event.getPlayer().sendMessage(
                                ChatColor.RED
                                + StyxSpace.prefix
                                + " You don't have permission to use this portal!");
                    }
                }
            }
        }
    }
}
