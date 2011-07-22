package me.iffa.styxspace.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceListener;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.utils.SpacePortalConfig;

/**
 * BlockListener for portal related events
 * 
 * @author iffa
 * 
 */
public class SpacePortalBListener extends BlockListener {
	public StyxSpace plugin;
	private SpacePlayer spacePlayer = new SpacePlayer();

	public SpacePortalBListener(StyxSpace instance) {
		plugin = instance;
	}

	/**
	 * Called when a sign is changed (after pressing the 'Done' button)
	 */
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).equalsIgnoreCase("[StyxSpace]")
				&& SpacePortalConfig.config.getProperty("StyxSpacePortals."
						+ event.getLine(1) + ".world") == null) {
			if (spacePlayer.hasPermission("StyxSpace.portal.create", player)) {
				Sign sign = (Sign) event.getBlock().getState();
				SpacePortalConfig.config.setProperty("StyxSpacePortals."
						+ event.getLine(1) + ".world", sign.getWorld()
						.getName());
				SpacePortalConfig.config.setProperty("StyxSpacePortals."
						+ event.getLine(1) + ".x", sign.getX());
				SpacePortalConfig.config.setProperty("StyxSpacePortals."
						+ event.getLine(1) + ".y", sign.getY());
				SpacePortalConfig.config.setProperty("StyxSpacePortals."
						+ event.getLine(1) + ".z", sign.getZ());
				SpacePortalConfig.config.save();
				event.getPlayer().sendMessage(
						ChatColor.GREEN + StyxSpace.prefix
								+ " Created portal '" + event.getLine(1)
								+ "' with destination '" + event.getLine(2)
								+ "'");
				// Notify listeners.
				for (SpaceListener listener : StyxSpace.listeners)
					listener.onPortalCreate(event.getPlayer(), sign.getLine(1));
				return;
			} else {
				event.getPlayer()
						.sendMessage(
								ChatColor.RED
										+ StyxSpace.prefix
										+ " You don't have the permission to create a portal!");
				return;
			}
		}
	}

	/**
	 * Called when a player removes a block
	 */
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (event.getBlock().getType() == Material.SIGN_POST
				|| event.getBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) event.getBlock().getState();
			if (sign.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
				if (spacePlayer.hasPermission("StyxSpace.portal.destroy",
						player)) {
					String name = sign.getLine(1);
					if (SpacePortalConfig.config
							.getProperty("StyxSpacePortals." + name) != null) {
						SpacePortalConfig.config
								.removeProperty("StyxSpacePortals."
										+ sign.getLine(1));
						SpacePortalConfig.config.save();
						event.getPlayer().sendMessage(
								ChatColor.GREEN
										+ "[StyxSpace] Removed portal '" + name
										+ "'");
						// Notify listeners.
						for (SpaceListener listener : StyxSpace.listeners)
							listener.onPortalRemove(event.getPlayer(),
									sign.getLine(1));
					}
				} else {
					event.setCancelled(true);
					event.getPlayer()
							.sendMessage(
									ChatColor.RED
											+ "[StyxSpace] You're not allowed to destroy portals!");
				}
			}
		} else {
			if (!spacePlayer.hasPermission("StyxSpace.portal.destroy", player)) {
				Location loc = event.getBlock().getLocation();
				loc.setY(loc.getY() + 1);
				if (loc.getBlock().getType() == Material.SIGN_POST) {
					Sign s = (Sign) loc.getBlock().getState();
					if (!spacePlayer.hasPermission("StyxSpace.portal.destroy",
							player)
							&& s.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
						event.setCancelled(true);
						event.getPlayer()
								.sendMessage(
										ChatColor.RED
												+ "[StyxSpace] You're not allowed to destroy portals!");
						return;
					}
				}
				if (event.getBlock().getType() != Material.WALL_SIGN
						&& event.getBlock().getType() != Material.SIGN_POST) {
					if (event.getBlock().getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {
						Sign s = (Sign) event.getBlock()
								.getRelative(BlockFace.SOUTH).getState();
						if (!player.hasPermission("StyxSpace.portal.destroy")
								&& s.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ "[StyxSpace] You're not allowed to destroy portals!");
							event.setCancelled(true);
							return;
						}
					} else if (event.getBlock().getRelative(BlockFace.NORTH)
							.getType() == Material.WALL_SIGN) {
						Sign s = (Sign) event.getBlock()
								.getRelative(BlockFace.NORTH).getState();
						if (!player.hasPermission("StyxSpace.portal.destroy")
								&& s.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ "[StyxSpace] You're not allowed to destroy portals!");
							event.setCancelled(true);
							return;
						}
					} else if (event.getBlock().getRelative(BlockFace.WEST)
							.getType() == Material.WALL_SIGN) {
						Sign s = (Sign) event.getBlock()
								.getRelative(BlockFace.WEST).getState();
						if (!player.hasPermission("StyxSpace.portal.destroy")
								&& s.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ "[StyxSpace] You're not allowed to destroy portals!");
							event.setCancelled(true);
							return;
						}
					} else if (event.getBlock().getRelative(BlockFace.EAST)
							.getType() == Material.WALL_SIGN) {
						Sign s = (Sign) event.getBlock()
								.getRelative(BlockFace.EAST).getState();
						if (!player.hasPermission("StyxSpace.portal.destroy")
								&& s.getLine(0).equalsIgnoreCase("[StyxSpace]")) {
							event.getPlayer()
									.sendMessage(
											ChatColor.RED
													+ "[StyxSpace] You're not allowed to destroy portals!");
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * Called when a block physics event happens
	 */
	public void onBlockPhysics(BlockPhysicsEvent event) {
		if (event.getBlock().getType() == Material.SIGN_POST
				|| event.getBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) event.getBlock().getState();
			if (sign.getLine(0).equalsIgnoreCase("[StyxSpace]")
					&& SpacePortalConfig.config.getProperty("StyxSpacePortals."
							+ sign.getLine(1)) != null) {
				SpacePortalConfig.config.removeProperty("StyxSpacePortals."
						+ sign.getLine(1));
				SpacePortalConfig.config.save();
			}
		}
	}
}