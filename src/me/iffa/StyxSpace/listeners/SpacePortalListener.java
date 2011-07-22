package me.iffa.styxspace.listeners;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceListener;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.utils.SpacePortalConfig;

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
	public SpacePlayer spacePlayer = new SpacePlayer();

	public SpacePortalListener(StyxSpace instance) {
	}

	/**
	 * Called when a player interacts with something
	 */
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
						if (SpacePortalConfig.config
								.getProperty("StyxSpacePortals."
										+ sign.getLine(2) + ".world") == null
								|| SpacePortalConfig.config
										.getProperty("StyxSpacePortals."
												+ sign.getLine(2) + ".x") == null
								|| SpacePortalConfig.config
										.getProperty("StyxSpacePortals."
												+ sign.getLine(2) + ".y") == null
								|| SpacePortalConfig.config
										.getProperty("StyxSpacePortals."
												+ sign.getLine(2) + ".z") == null) {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ StyxSpace.prefix
													+ " The destination doesn't exist!");
							return;
						} else {
							Location tpDest = new Location(
									Bukkit.getServer().getWorld(
											SpacePortalConfig.config.getString(
													"StyxSpacePortals."
															+ sign.getLine(2)
																	.trim()
															+ ".world", Bukkit
															.getServer()
															.getWorlds().get(0)
															.getName())),
									SpacePortalConfig.config.getDouble(
											"StyxSpacePortals."
													+ sign.getLine(2).trim()
													+ ".x", Bukkit.getServer()
													.getWorlds().get(0)
													.getSpawnLocation().getX()),
									SpacePortalConfig.config.getDouble(
											"StyxSpacePortals."
													+ sign.getLine(2).trim()
													+ ".y", Bukkit.getServer()
													.getWorlds().get(0)
													.getSpawnLocation().getY()),
									SpacePortalConfig.config.getDouble(
											"StyxSpacePortals."
													+ sign.getLine(2).trim()
													+ ".z", Bukkit.getServer()
													.getWorlds().get(0)
													.getSpawnLocation().getZ()));
							if (tpDest.getWorld() != event.getPlayer()
									.getWorld()) {
								if (event.getPlayer().getWorld() != StyxSpace
										.getSpace()
										&& tpDest.getWorld() == StyxSpace
												.getSpace()) {
									event.getPlayer().teleport(tpDest);
									// Notify listeners.
									for (SpaceListener listener : StyxSpace.listeners)
										listener.onTeleportToSpace(
												event.getPlayer(), tpDest);
									return;
								} else if (event.getPlayer().getWorld() == StyxSpace
										.getSpace()
										&& tpDest.getWorld() != StyxSpace
												.getSpace()) {
									event.getPlayer().teleport(tpDest);
									// Notify listeners.
									for (SpaceListener listener : StyxSpace.listeners)
										listener.onTeleportToSpace(
												event.getPlayer(), tpDest);
									return;
								}
							}
						}
					} else {
						event.getPlayer()
								.sendMessage(
										ChatColor.RED
												+ StyxSpace.prefix
												+ " You don't have permission to use this portal!");
					}
				}
			}
		}
	}
}
